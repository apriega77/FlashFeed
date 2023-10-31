package skeleton.data.base

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import skeleton.model.base.PreferenceKey
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

private const val KEY_ALIAS = "_android_super_secret"

@RequiresApi(Build.VERSION_CODES.M)
private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES

@RequiresApi(Build.VERSION_CODES.M)
private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC

@RequiresApi(Build.VERSION_CODES.M)
private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7

@RequiresApi(Build.VERSION_CODES.M)
private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"

// TODO : Encrypt Key -> Current Soltion is using Base64 to encode and decode key
@RequiresApi(Build.VERSION_CODES.M)
@Suppress("TooManyFunctions")
open class BasePreference(context: Context, name: String) : SharedPreferences {

    private val preferences by lazy {
        context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    override fun getAll(): Map<String, Any> {
        return preferences.all.map { (key, value) ->
            val decryptedKey = decrypt(key).toString()
            val decryptedValue: Any = decrypt(value.toString())
            decryptedKey to decryptedValue
        }.toMap()
    }

    override fun getString(key: String, defaultValue: String?): String? {
        return getDecryptedObject(key) ?: defaultValue
    }

    override fun getStringSet(key: String, defaultValue: MutableSet<String>?): MutableSet<String>? {
        return getDecryptedObject(key) ?: defaultValue
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return getDecryptedObject(key) ?: defaultValue
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        return getDecryptedObject(key) ?: defaultValue
    }

    override fun getFloat(key: String, defaultValue: Float): Float {
        return getDecryptedObject(key) ?: defaultValue
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return getDecryptedObject(key) ?: defaultValue
    }

    override fun contains(key: String): Boolean {
        val encryptedKey = Base64.encodeToString(key.toByteArray(), Base64.NO_WRAP)
        return preferences.contains(encryptedKey)
    }

    override fun edit(): SharedPreferences.Editor {
        return Editor(this, preferences.edit())
    }

    override fun registerOnSharedPreferenceChangeListener(
        key: SharedPreferences.OnSharedPreferenceChangeListener?,
    ) {
        preferences.registerOnSharedPreferenceChangeListener(key)
    }

    override fun unregisterOnSharedPreferenceChangeListener(
        key: SharedPreferences.OnSharedPreferenceChangeListener?,
    ) {
        preferences.unregisterOnSharedPreferenceChangeListener(key)
    }

    private fun getEncryptCipher(): Cipher {
        return Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.ENCRYPT_MODE, getOrCreateSecretKey())
        }
    }

    private fun getDecryptCipherForIv(iv: ByteArray): Cipher {
        return Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.DECRYPT_MODE, getOrCreateSecretKey(), IvParameterSpec(iv))
        }
    }

    private fun getOrCreateSecretKey(): SecretKey {
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)
        if (keyStore.containsAlias(KEY_ALIAS)) {
            return keyStore.getKey(KEY_ALIAS, null) as SecretKey
        }
        return createSecretKey()
    }

    private fun createSecretKey(): SecretKey {
        return KeyGenerator.getInstance(ALGORITHM).apply {
            init(
                KeyGenParameterSpec.Builder(
                    KEY_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT,
                )
                    .setBlockModes(BLOCK_MODE)
                    .setEncryptionPaddings(PADDING)
                    .setRandomizedEncryptionRequired(true)
                    .build(),
            )
        }.generateKey()
    }

    private fun encrypt(value: ByteArray, type: EncryptedType): String {
        val cipher = getEncryptCipher()
        val encryptedBytes = cipher.doFinal(value)
        return listOf(cipher.iv, type.name.toByteArray(), encryptedBytes).joinToString("$") {
            Base64.encodeToString(it, Base64.NO_WRAP)
        }
    }

    private fun encryptKeyValuePair(
        key: String,
        value: ByteArray,
        type: EncryptedType,
    ): Pair<String, String> {
        return Base64.encodeToString(key.toByteArray(), Base64.NO_WRAP) to encrypt(value, type)
    }

    private inline fun <T : Any> getDecryptedObject(key: String): T? {
        val encryptedKey = Base64.encodeToString(key.toByteArray(), Base64.NO_WRAP)
        return preferences.getString(encryptedKey, null)?.let { encryptedValue ->
            decrypt(encryptedValue) as? T
        }
    }

    private fun decrypt(encrypted: String): Any {
        val (iv, type, values) = encrypted.split("$").map { Base64.decode(it, Base64.NO_WRAP) }
        val cipher = getDecryptCipherForIv(iv)
        val decryptedType = EncryptedType.valueOf(String(type))
        val decryptedValue = String(cipher.doFinal(values))
        return when (decryptedType) {
            EncryptedType.STRING -> decryptedValue
            EncryptedType.STRING_SET -> decryptedValue.split(",").toSet()
            EncryptedType.INT -> decryptedValue.toInt()
            EncryptedType.LONG -> decryptedValue.toLong()
            EncryptedType.FLOAT -> decryptedValue.toFloat()
            EncryptedType.BOOLEAN -> decryptedValue.toBoolean()
        }
    }

    fun clearCache() {
        edit().clear().apply()
    }

    fun removeCache(key: String) {
        edit().remove(key).apply()
    }

    inline fun <reified T : Any, Key : PreferenceKey<T>> getData(key: Key): T? {
        return try {
            val stringData = getString(key.key, null) ?: return null
            Gson().fromJson(stringData, T::class.java)
        } catch (e: Exception) {
            null
        }
    }

    inline fun <reified T : Any?, Key : PreferenceKey<T>> getData(
        preferenceKey: Key,
        defaultValue: T,
    ): T {
        return try {
            val stringData = getString(preferenceKey.key, null) ?: return defaultValue
            Gson().fromJson(stringData, T::class.java)
        } catch (e: Exception) {
            defaultValue
        }
    }

    fun <T : Any, Key : PreferenceKey<T>> putData(key: Key, data: T) {
        val stringData = Gson().toJson(data)
        edit().putString(key.key, stringData).apply()
    }

    class Editor(
        private val basePreference: BasePreference,
        private val editor: SharedPreferences.Editor,
    ) : SharedPreferences.Editor by editor {

        override fun putString(key: String, value: String?): SharedPreferences.Editor = apply {
            putEncryptedObject(key, value.toString().toByteArray(), EncryptedType.STRING)
        }

        override fun putStringSet(
            key: String,
            value: MutableSet<String>?,
        ): SharedPreferences.Editor = apply {
            putEncryptedObject(
                key,
                value.orEmpty().joinToString(",").toByteArray(),
                EncryptedType.BOOLEAN,
            )
        }

        override fun putInt(key: String, value: Int): SharedPreferences.Editor = apply {
            putEncryptedObject(key, value.toString().toByteArray(), EncryptedType.INT)
        }

        override fun putLong(key: String, value: Long): SharedPreferences.Editor = apply {
            putEncryptedObject(key, value.toString().toByteArray(), EncryptedType.LONG)
        }

        override fun putFloat(key: String, value: Float): SharedPreferences.Editor = apply {
            putEncryptedObject(key, value.toString().toByteArray(), EncryptedType.FLOAT)
        }

        override fun putBoolean(key: String, value: Boolean): SharedPreferences.Editor = apply {
            putEncryptedObject(key, value.toString().toByteArray(), EncryptedType.BOOLEAN)
        }

        override fun remove(key: String): SharedPreferences.Editor {
            val encryptedKey = Base64.encodeToString(key.toByteArray(), Base64.NO_WRAP)
            editor.remove(encryptedKey)
            return this
        }

        override fun clear(): SharedPreferences.Editor {
            return editor.clear()
        }

        override fun commit(): Boolean {
            return editor.commit()
        }

        override fun apply() {
            editor.apply()
        }

        private fun putEncryptedObject(key: String, value: ByteArray, type: EncryptedType) {
            val (k, v) = basePreference.encryptKeyValuePair(key, value, type)
            editor.putString(k, v)
        }
    }

    private enum class EncryptedType {
        STRING,
        STRING_SET,
        INT,
        LONG,
        FLOAT,
        BOOLEAN,
    }
}
