package com.example.anew.logic.list

import com.example.anew.data.entities.LoginUser
import com.example.anew.logic.data.MarvelChars

class ListItem {

    fun returnItems(): List<LoginUser> {
        var items = listOf<LoginUser>(
            LoginUser("1", "1"),
            LoginUser("2", "2"),
            LoginUser("3", "3"),
            LoginUser("4", "4"),
            LoginUser("5", "5")
        )
        return items
    }

    fun returnMarvelChars(): List<MarvelChars>{
        val item = listOf(
            MarvelChars(
                1,
                "Daredevil",
                "Daredevil",
                "https://comicvine.gamespot.com/a/uploads/scale_small/11118/111187046/7397359-0398898002-EQH1ysWWsAA7QLf"
            ),
            MarvelChars(
                2,
                "Spider-Woman",
                "The Spider-Woman",
                "https://comicvine.gamespot.com/a/uploads/scale_small/11116/111167641/7822219-spiderwoman_issue11-art.jpg"
            ),
            MarvelChars(
                3,
                "Wonder Man",
                "The Avengers",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/7817314-simon_williams_%28earth-616%29_from_avengers_vol_1_685_001.jpg"
            ),
            MarvelChars(
                4,
                "Squirrel Girl",
                "The Unbeatable Squirrel Girl",
                "https://comicvine.gamespot.com/a/uploads/scale_small/6/67663/6923388-44-variant.jpg"
            ),
            MarvelChars(
                5,
                "Spider-Man 2099",
                "Spider-Man 2099",
                "https://comicvine.gamespot.com/a/uploads/scale_small/11170/111705043/8700768-spider-man2099.jpg"
            )
        )
        return item
    }
}