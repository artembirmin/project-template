package com.incetro.projecttemplate.presentation.userstory.demo.demoscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.incetro.projecttemplate.presentation.base.mvikotlin.BaseMVIComposeFragment
import com.incetro.projecttemplate.presentation.userstory.demo.di.DemoComponent
import com.incetro.projecttemplate.utils.ext.collectAsStateWithLifecycle
import javax.inject.Inject

class DemoFragment : BaseMVIComposeFragment() {

    @Inject
    lateinit var storeFactory: DemoStoreFactory.Factory
    override val store: DemoStore by lazy { storeFactory.create(3, stateKeeper).store() }
    override val storeName = DemoStore.NAME

    override fun inject() = DemoComponent.Manager.getComponent().inject(this)

    override fun release() = Unit

    @Composable
    override fun CreateView() {
        val viewState: DemoStore.State by store.states
            .collectAsStateWithLifecycle(DemoStore.State())
        MaterialTheme() {
            Counter(viewState)
        }
    }

    @androidx.compose.ui.tooling.preview.Preview(
        backgroundColor = 0xFFFFFFFF,
        device = "spec:width=1080px,height=1920px,dpi=440"
    )
    @Composable
    fun Counter(
        viewState: DemoStore.State = DemoStore.State(
            counter = 12,
            numberFact = "qqqqkqqqqqqqqqqqqqqqqq2wqqqqqq"
        )
    ) {
        Surface(modifier = Modifier.padding(16.dp)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Card(
                    elevation = 8.dp,
                    modifier = Modifier.padding(bottom = 16.dp),
                    shape = RoundedCornerShape(8.dp),
                    backgroundColor = Color.Magenta,
                ) {
                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .defaultMinSize(minWidth = 120.dp),
                        text = viewState.counter.toString(),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }

                Row() {
                    Button(
                        onClick = { store.accept(DemoStore.Intent.DecreaseCounter) },
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Text(text = "-")
                    }
                    Button(
                        onClick = { store.accept(DemoStore.Intent.IncreaseCounter) },
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Text(text = "+")
                    }
                }
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = viewState.numberFact,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    companion object {
        fun newInstance() = DemoFragment()
    }
}