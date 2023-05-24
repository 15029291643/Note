package com.example.note.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

val job = Job()
val coroutinesScope = CoroutineScope(Dispatchers.IO + job)