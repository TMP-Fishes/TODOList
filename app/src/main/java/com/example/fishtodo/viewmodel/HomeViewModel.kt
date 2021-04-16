package com.example.fishtodo.viewmodel

import com.example.fishtodo.mongodb.FishCollection

class HomeViewModel { // TODO: how about "loading animation"?
    var title = "Have a cup of coffee ~"
    var taskString = "Loading ~ Or NONE?" // TODO: how about "list"?

    fun bind(model: FishCollection?) {
        model?.let {
            title = model.greeting.toString()
            taskString = ""
            model.tasks?.map { task ->
                taskString += task.toString()
            }
        }
    }
}