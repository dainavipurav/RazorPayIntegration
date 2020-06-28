package com.example.razorpayintegration

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.IntegerRes
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class MainActivity : AppCompatActivity(), View.OnClickListener , PaymentResultListener{

    private lateinit var mEtAmount: EditText
    private lateinit var mTvAmount: TextView
    private lateinit var mBtnAmount: Button
    private var mAmount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mEtAmount = findViewById(R.id.et_amount)
        mTvAmount = findViewById(R.id.tv_amount)

        mBtnAmount = findViewById(R.id.btn_amount)
        mBtnAmount.setOnClickListener(this@MainActivity)

        Checkout.preload(applicationContext)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_amount -> {
                startPayment()
            }
        }
    }

    fun startPayment() {
        mAmount = mEtAmount.text.toString().toInt()
        val activity:Activity = this
        val co = Checkout()

        try {
            val options = JSONObject()
            options.put("name","Razorpay Corp")
            options.put("description","Demoing Charges")
            //You can omit the image option to fetch the image from dashboard
            options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("currency","INR")
            options.put("amount",mAmount*100)

           /* val prefill = JSONObject()
            prefill.put("email","test@razorpay.com")
            prefill.put("contact","9876543210")

            options.put("prefill",prefill)*/
            co.open(activity,options)
        }catch (e: Exception){
            Toast.makeText(activity,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this,"Payment Error: ${p1}",Toast.LENGTH_LONG).show()
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this,"Payment Successful: ${p0}",Toast.LENGTH_LONG).show()
    }
}
