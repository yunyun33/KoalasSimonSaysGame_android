package com.yui.koalassimonsaysgame_android.resultPage

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.yui.koalassimonsaysgame_android.MainActivity
import com.yui.koalassimonsaysgame_android.R

class RegisterRankingDialogFragment : DialogFragment(), RegisterRankingDialogFragmentContract.View {

    private lateinit var presenter: RegisterRankingDialogFragmentContract.Presenter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val intent = Intent(activity, RegisterRankingDialogFragment::class.java)
        presenter = RegisterRankingDialogFragmentPresenter(this, intent)

        val builder = AlertDialog.Builder(activity)
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.activity_register_ranking_dialog_fragment, null)

        builder.setView(view)
            .setPositiveButton(this.getString(R.string.registerForRankingDialog_positiveText), DialogInterface.OnClickListener { dialog, which ->

                // OKボタン押したときの処理(rankingに登録する)
                val nameText = view.findViewById<EditText>(R.id.editText)
                nameText.setHint(this.getString(R.string.registerForRankingDialog_editText_hint))

                val userText = nameText.getText().toString().trim()

                val worldRankingSwitch = view.findViewById<Switch>(R.id.worldRankingSwitch)
                if (worldRankingSwitch.isChecked == true) {

                    // 端末のrankingとworldRankingの両方に登録する。
                    presenter.didTapRegisterButton(userText, true)
                    Toast.makeText(activity, "worldRankingに登録するよ", Toast.LENGTH_SHORT).show()

                } else {

                    // 端末のrankingだけ登録する。
                    presenter.didTapRegisterButton(userText, false)
                }
            })

            .setNegativeButton(this.getString(R.string.registerForRankingDialog_negativeText), DialogInterface.OnClickListener { dialog, which ->

                // 登録しない場合は、トップページに戻る。
                presenter.didTapNoRegisterButton()
            })

        return builder.create()
    }

    companion object {
        const val bundleKeyTotalScore = "totalScoreForFragment"

        fun newInstance(totalScore: Int): RegisterRankingDialogFragment {
            val fragment = RegisterRankingDialogFragment()
            val bundle = Bundle()
            bundle.putInt(bundleKeyTotalScore, totalScore)
            fragment.arguments = bundle
            return fragment
        }
    }

    // RegisterRankingDialogFragmentContract.View

    override fun backToStartPage() {

        val intent = Intent(activity, MainActivity::class.java)
        // 今までのページのインスタンスは破棄される。(Activityのスタックを消すようにする。(Fragmentも消える))
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun showEmptyErrorMessage() {
        Toast.makeText(activity, this.getString(R.string.emptyErrorMessage), Toast.LENGTH_LONG).show()
    }
}