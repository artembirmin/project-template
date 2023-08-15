package  com.incetro.projecttemplate.presentation.base.mvikotlin

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal val mainDispatcher: CoroutineDispatcher = Dispatchers.Main

internal val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
