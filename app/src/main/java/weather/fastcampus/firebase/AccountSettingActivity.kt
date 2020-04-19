package weather.fastcampus.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_account_setting.*

class AccountSettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_setting)

        setUpListener()
    }

    private fun setUpListener(){
        account_setting_back.setOnClickListener{ onBackPressed() }

        account_setting_logout.setOnClickListener { signoutAccount() }

        account_setting_delete.setOnClickListener { showDeleteDialog() }
    }

    private fun signoutAccount(){
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                startActivity(MainActivity::class.java)
                simpleToastLong("LOGOUT!", this)
            }
    }

    private fun deleteAccount(){
        AuthUI.getInstance()
            .delete(this)
            .addOnCompleteListener {
                startActivity(MainActivity::class.java)
                simpleToastLong("Your Account is Delete!", this)
            }
    }

    private fun showDeleteDialog(){
        AccountDeleteDialog().apply {
            addAccountDeleteDialogInterface(object : AccountDeleteDialog.AccountDeleteDialogInterface{
                override fun delete() {
                    deleteAccount()
                }
                override fun cancel_delete() {
                }
            })
        }.show(supportFragmentManager, "")
    }


}
