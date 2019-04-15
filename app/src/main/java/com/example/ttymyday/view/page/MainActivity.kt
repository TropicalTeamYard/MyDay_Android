package com.example.ttymyday.view.page

import android.content.Intent
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.example.ttymyday.R
import com.example.ttymyday.data.DataSource
import com.example.ttymyday.data.UserUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() ,BottomNavigationView.OnNavigationItemSelectedListener {

    //region Fields And Function With navigation
    private lateinit var fragments:Array<Fragment>
    private var fragmentindex:Int = 0

    private fun switchFragment(index:Int){
        if (index != fragmentindex){
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            transaction.hide(fragments[fragmentindex])
            if (!fragments[index].isAdded){
                transaction.add(R.id.contentView,fragments[index])
            }
            transaction.show(fragments[index]).commitAllowingStateLoss()

            tbx_main_title.text = when(index){
                0->{getString(R.string.title_home)}
                1->{getString(R.string.title_schedule)}
                2->{getString(R.string.title_team)}
                3->{getString(R.string.title_news)}
                4->{getString(R.string.title_my)}
                else -> "null"
            }
        }

        fragmentindex = index
    }
    //endregion

    private fun initFragment(){
        val homeFragment:Fragment = HomeFragment()
        val scheduleFragment: Fragment = ScheduleFragment()
        val teamFragment: Fragment = TeamFragment()
        val newsFragment: Fragment = NewsFragment()
        val myFragment: Fragment = MyFragment()
        fragments = arrayOf<Fragment>(homeFragment, scheduleFragment,teamFragment,newsFragment,myFragment)
        fragmentindex = 0
        //switchFragment(0)
        supportFragmentManager.beginTransaction()
            .replace(R.id.contentView,fragments[fragmentindex])
            .show(fragments[fragmentindex])
            .commit()
        navigation.setOnNavigationItemSelectedListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFragment()

        //region 判断是否已经加载，此处存放初次加载的代码
//        if (!DataSource.isLoad){
//            DataSource.isLoad = true
//            DataSource.loadUser(this)
//            Log.d(TAG,"首次加载，usertype=${DataSource.user.usertype}")
//            //说明用户并没有登录，将跳到登录界面，而且无法返回。
//            if (DataSource.user.usertype == "#NONE"){
//                val intent = Intent(this, LoginActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                startActivity(intent)
//            } else if (DataSource.user.usertype =="#CLOUD") {
//                Log.d(TAG,"正在验证自动登录")
//                UserUtil.autoLogin()
//            }
//        }
        //endregion
    }

    //region Events
    override fun onNavigationItemSelected(@NonNull menuItem: MenuItem): Boolean {
        when (menuItem.itemId){
            R.id.navigation_home ->{
                switchFragment(0)
                return true
            }
            R.id.navigation_schedule -> {
                switchFragment(1)
                return true
            }
            R.id.navigation_team ->{
                switchFragment(2)
                return true
            }
            R.id.navigation_news ->{
                switchFragment(3)
                return true
            }
            R.id.navigation_my -> {
                switchFragment(4)
                return true
            }
            else ->{return false}
        }
    }
    //endregion

    companion object{
        val TAG:String = "MainActivity"
    }
}
