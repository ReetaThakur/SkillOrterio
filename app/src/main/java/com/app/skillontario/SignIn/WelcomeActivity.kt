package com.app.skillontario.SignIn

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.app.skillontario.activities.SelectLanguage
import com.app.skillontario.activities.SelectRoleActivity
import com.app.skillontario.baseClasses.BaseActivity
import com.app.skillontario.constants.AppConstants
import com.app.skillontario.signup.SignUpActivity
import com.app.skillontario.utils.MySharedPreference
import com.app.skillontario.utils.Utils
import com.app.skillorterio.R
import com.app.skillorterio.databinding.ActivityWelcomeBinding

class WelcomeActivity : BaseActivity() {
    private var binding: ActivityWelcomeBinding? = null

    override fun initUi() {
        Utils.changeStatusBarColor(this, this)
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left)
        binding = viewBaseBinding as ActivityWelcomeBinding

        binding!!.llNext.setOnClickListener {
            if (binding!!.viewPager.currentItem == 3) {
                openLoginPage()
            } else
                binding!!.viewPager.currentItem = binding!!.viewPager.currentItem + 1
        }


        binding!!.tvSkip.setOnClickListener { openLoginPage() }

        setupPager()
    }

    private fun openLoginPage() {
        MySharedPreference.getInstance().setBooleanData(AppConstants.IS_WALK_THROUGH, true)
        startActivity(Intent(this, SignUpActivity::class.java))
        finishAffinity()
    }

    private fun setupPager() {
        val adapter = PagerAdapter(this@WelcomeActivity)
        binding!!.viewPager.adapter = adapter
        // binding!!.viewPager.transitionName=""
        binding!!.viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                /*if (position == 0)
                    binding!!.ivPrev.visibility = View.GONE
                else
                    binding!!.ivPrev.visibility = View.VISIBLE*/

                if (position == 3) {
                    binding!!.tvNext.setText(getString(R.string.get_stareded))
                    //  binding!!.tvNext.visibility = View.GONE
                    // binding!!.tvStarted.visibility = View.VISIBLE
                } else {
                    binding!!.tvNext.setText(getString(R.string.next1))
                    //  binding!!.tvNext.visibility = View.VISIBLE
                    // binding!!.tvStarted.visibility = View.GONE
                }

            }

            override fun onPageSelected(position: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    class PagerAdapter internal constructor(mContext: Context) :
        androidx.viewpager.widget.PagerAdapter() {
        private var context: Context = mContext
        private val inflater: LayoutInflater =
            mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return 4
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            var view: View? = null
            if (position == 0) {
                view = inflater.inflate(R.layout.step_1, container, false)
            } else if (position == 1) {
                view = inflater.inflate(R.layout.step_2, container, false)

            } else if (position == 2) {
                view = inflater.inflate(R.layout.step_3, container, false)
            } else if (position == 3) {
                view = inflater.inflate(R.layout.step_4, container, false)
            }

            if (view != null) {
                view.tag = position
            }
            container.addView(view)
            return view!!
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

    }

    override fun getLayoutById(): Int {
        return R.layout.activity_welcome
    }

    public fun callOnNumbers() {

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }
}