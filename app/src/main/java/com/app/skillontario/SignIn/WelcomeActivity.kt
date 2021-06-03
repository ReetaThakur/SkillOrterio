package com.app.skillontario.SignIn

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.app.skillontario.activities.SelectLanguage
import com.app.skillontario.baseClasses.BaseActivity
import com.app.skillontario.constants.AppConstants
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
            if (binding!!.viewPager.currentItem == 2) {
                openLoginPage()
            } else
                binding!!.viewPager.currentItem = binding!!.viewPager.currentItem + 1
        }

        /*  binding!!.ivPrev.setOnClickListener {
              binding!!.viewPager.currentItem = binding!!.viewPager.currentItem - 1
          }*/

        //  binding!!.tvStarted.setOnClickListener { openLoginPage() }
        binding!!.tvSkip.setOnClickListener { openLoginPage() }

        setupPager()
    }

    private fun openLoginPage() {
        MySharedPreference.getInstance().setBooleanData(AppConstants.IS_WALK_THROUGH, true)
        startActivity(Intent(this, SelectLanguage::class.java))
        finishAffinity()
    }

    private fun setupPager() {
        val adapter = PagerAdapter(this@WelcomeActivity)
        binding!!.viewPager.adapter = adapter
        binding!!.viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                /*if (position == 0)
                    binding!!.ivPrev.visibility = View.GONE
                else
                    binding!!.ivPrev.visibility = View.VISIBLE*/

                if (position == 2) {
                    binding!!.tvNext.setText("Get Started")
                  //  binding!!.tvNext.visibility = View.GONE
                   // binding!!.tvStarted.visibility = View.VISIBLE
                } else {
                    binding!!.tvNext.setText("Next")
                  //  binding!!.tvNext.visibility = View.VISIBLE
                   // binding!!.tvStarted.visibility = View.GONE
                }

            }

            override fun onPageSelected(position: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    class PagerAdapter internal constructor(mContext: Context) : androidx.viewpager.widget.PagerAdapter() {
        private var context: Context = mContext
        private val inflater: LayoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return 3
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
              /*  val tvPhone = view.findViewById<View>(R.id.tv_call)
                val tvWeb = view.findViewById<View>(R.id.tv_open_web)
                tvPhone.setOnClickListener {
                    callOnNumber(context as WelcomeActivity, "18336297653")
                }

                tvWeb.setOnClickListener {
                    val intent = Intent(context, TNCActivity::class.java)
                    intent.putExtra("title", "")
                    intent.putExtra("url", "https://go.maxsold.com/sell-with-maxsold")
                    context.startActivity(intent)
                }*/

            } else if (position == 2) {
                view = inflater.inflate(R.layout.step_3, container, false)
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
}