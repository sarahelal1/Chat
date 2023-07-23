package com.example.chatapp.ui.register_activity.model

import com.example.chatapp.R

data class Category(
    var id:String,
    var title:String,
    var imageId:Int

){
    companion object{
        val categories= mutableListOf<Category>(
            Category(id="sports", title = "Sports",imageId= R.drawable.sports),
                    Category(id="music", title = "Music",imageId= R.drawable.music),
        Category(id="movies", title = "Movies",imageId= R.drawable.movies)
        )
        fun grtCategoryFromId(id:String):Category{
when (id){
    "sports"->{
        return categories[0];
    }
"music"-> {return categories[1]};
    "movies"-> {return categories[2]};
}
            return categories[0]
        }
    }
}
