package com.ketabkhan.reader.util

import com.ketabkhan.reader.data.model.Chapter
import com.ketabkhan.reader.data.model.Footnote
import org.json.JSONArray
import org.json.JSONObject

object BookJsonParser {

    fun chaptersToJson(chapters: List<Chapter>): String {
        val array = JSONArray()
        chapters.forEach { ch ->
            val obj = JSONObject()
            obj.put("id", ch.id)
            obj.put("title", ch.title)
            obj.put("level", ch.level)
            obj.put("confident", ch.confident)
            obj.put("content", ch.content)
            if (ch.imageCaption != null) {
                obj.put("imageCaption", ch.imageCaption)
            }
            val fnArray = JSONArray()
            ch.footnotes.forEach { fn ->
                val fnObj = JSONObject()
                fnObj.put("id", fn.id)
                fnObj.put("number", fn.number)
                fnObj.put("text", fn.text)
                fnArray.put(fnObj)
            }
            obj.put("footnotes", fnArray)
            array.put(obj)
        }
        return array.toString()
    }

    fun jsonToChapters(json: String): List<Chapter> {
        if (json.isBlank() || json == "[]") return emptyList()
        return try {
            val array = JSONArray(json)
            val list = mutableListOf<Chapter>()
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                val fnList = mutableListOf<Footnote>()
                if (obj.has("footnotes")) {
                    val fnArray = obj.getJSONArray("footnotes")
                    for (j in 0 until fnArray.length()) {
                        val fnObj = fnArray.getJSONObject(j)
                        fnList.add(
                            Footnote(
                                id = fnObj.optString("id", "fn_$j"),
                                number = fnObj.optString("number", "${j + 1}"),
                                text = fnObj.optString("text", "")
                            )
                        )
                    }
                }
                list.add(
                    Chapter(
                        id = obj.optString("id", "c_$i"),
                        title = obj.optString("title", "فصل ${i + 1}"),
                        level = obj.optInt("level", 0),
                        confident = obj.optBoolean("confident", true),
                        content = obj.optString("content", ""),
                        footnotes = fnList,
                        imageCaption = if (obj.has("imageCaption")) obj.getString("imageCaption") else null
                    )
                )
            }
            list
        } catch (e: Exception) {
            emptyList()
        }
    }
}
