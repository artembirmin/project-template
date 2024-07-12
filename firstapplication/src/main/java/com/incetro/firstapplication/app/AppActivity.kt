/*
 * ProjectTemplate
 *
 * Created by artembirmin on 3/11/2022.
 */

package com.incetro.firstapplication.app

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.incetro.firstapplication.R
import com.incetro.firstapplication.common.di.activity.ActivityComponent
import com.incetro.firstapplication.common.di.qualifier.AppNavigation
import com.incetro.firstapplication.presentation.base.mvvm.view.BackPressedListener
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

class AppActivity : AppCompatActivity() {

    @Inject
    lateinit var appLauncher: AppLauncher

    @Inject
    @AppNavigation
    lateinit var navigatorHolder: NavigatorHolder

    /**
     * Instance of currently displayed fragment
     */
    private val currentFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.fragment_container)

    private val navigator: Navigator = AppNavigator(this, R.id.fragment_container)

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
//        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.layout_container)

        if (savedInstanceState == null) {
            appLauncher.start()
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressedClick()
            }
        }
        onBackPressedDispatcher.addCallback(
            owner = this,
            onBackPressedCallback = callback
        )
    }

    private fun inject() {
        ActivityComponent.Manager.getComponent().inject(this)
    }

    fun onBackPressedClick() {
        (currentFragment as? BackPressedListener)?.onBackPressed() ?: finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            compositeDisposable.clear()
            ActivityComponent.Manager.releaseComponent()
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    protected fun Disposable.addDisposable(): Disposable {
        compositeDisposable.add(this)
        return this
    }
}