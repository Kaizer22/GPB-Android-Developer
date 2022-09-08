package otus.gpb.homework.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar


class EditProfileActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "EditProfileActivity"
    }

    private lateinit var imageView: ImageView

    private val actionMakePhoto by lazy {Pair(0, getString(R.string.action_make_photo))}
    private val actionChoosePhoto by lazy {Pair(1, getString(R.string.action_choose_photo))}
    private val addImageActions by lazy {arrayOf(actionMakePhoto.second, actionChoosePhoto.second)}

    private val onClickAddImage = View.OnClickListener {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.alert_dialog_title))
            .setItems(addImageActions) { _, option ->
                when (option) {
                    actionMakePhoto.first -> requestPermission()
                    actionChoosePhoto.first -> {
                        Snackbar.make(imageView, "Choose photo",
                            Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
            .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        imageView.setOnClickListener(onClickAddImage)

        findViewById<Toolbar>(R.id.toolbar).apply {
            inflateMenu(R.menu.menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.send_item -> {
                        openSenderApp()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun showCat() {
        imageView.setImageDrawable(
            ActivityCompat.getDrawable(this, R.drawable.cat)
        )
    }

    private val openSettingsLauncher = registerForActivityResult(
        object: ActivityResultContract<String, Boolean>() {
            override fun createIntent(context: Context, input: String): Intent {
                return Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:$packageName")
                )
            }

            override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
                return ContextCompat.checkSelfPermission(
                        this@EditProfileActivity, Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            }
        }
    ) {
        if (!it) {
            Snackbar.make(imageView, getString(R.string.settings_result_negative),
                Snackbar.LENGTH_SHORT).show()
        } else { showCat() }
    }

    private val requestCameraLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        granted ->
        if (granted) {
            showCat()
        } else if (!ActivityCompat.shouldShowRequestPermissionRationale(
            this, Manifest.permission.CAMERA)) {
            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.settings_alert_title)
                .setPositiveButton(R.string.open_settings) {
                        _,_ -> openSettingsLauncher.launch("")
                }.show()
        }
    }

    private fun requestPermissionWithRationale() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.rationale_title))
            .setMessage(getString(R.string.rationale_message))
            .setPositiveButton(getString(R.string.rationale_positive)) {
                    _, _-> requestCameraLauncher.launch(Manifest.permission.CAMERA)
            }
            .setNegativeButton(getString(R.string.rationale_negative)) {
                    dialog, _ -> dialog.cancel()
            }.show()
    }

    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            requestPermissionWithRationale()
        } else {
            requestCameraLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        TODO("В качестве реализации метода отправьте неявный Intent чтобы поделиться профилем. В качестве extras передайте заполненные строки и картинку")
    }
}