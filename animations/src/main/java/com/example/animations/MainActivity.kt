package com.example.animations

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.animations.animationsactivities.*

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        recycler_view.layoutManager = android.support.v7.widget.LinearLayoutManager(this)

        val items = ArrayList<RocketAnimationItem>()

        items.add(RocketAnimationItem(getString(R.string.title_no_animation),
                Intent(this, NoAnimationActivity::class.java)))

        items.add(RocketAnimationItem(getString(R.string.title_launch_rocket),
                Intent(this, LaunchRocketValueAnimatorAnimationActivity::class.java)))

        items.add(RocketAnimationItem(getString(R.string.title_spin_rocket),
                Intent(this, RotateRocketAnimationActivity::class.java)))

        items.add(RocketAnimationItem(getString(R.string.title_accelerate_rocket),
                Intent(this, AccelerateRocketAnimationActivity::class.java)))

        items.add(RocketAnimationItem(getString(R.string.title_launch_rocket_objectanimator),
                Intent(this, LaunchRocketObjectAnimatorAnimationActivity::class.java)))

        items.add(RocketAnimationItem(getString(R.string.title_color_animation),
                Intent(this, ColorAnimationActivity::class.java)))

        items.add(RocketAnimationItem(getString(R.string.launch_spin),
                Intent(this, LaunchAndSpinAnimatorSetAnimationActivity::class.java)))

        items.add(RocketAnimationItem(getString(R.string.launch_spin_viewpropertyanimator),
                Intent(this, LaunchAndSpinViewPropertyAnimatorAnimationActivity::class.java)))

        items.add(RocketAnimationItem(getString(R.string.title_with_doge),
                Intent(this, FlyWithDogeAnimationActivity::class.java)))

        items.add(RocketAnimationItem(getString(R.string.title_animation_events),
                Intent(this, WithListenerAnimationActivity::class.java)))

        items.add(RocketAnimationItem(getString(R.string.title_there_and_back),
                Intent(this, FlyThereAndBackAnimationActivity::class.java)))

        items.add(RocketAnimationItem(getString(R.string.title_jump_and_blink),
                Intent(this, XmlAnimationActivity::class.java)))

        recycler_view.adapter = RocketAdapter(this, items)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
