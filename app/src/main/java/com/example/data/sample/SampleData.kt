package com.example.data.sample

import com.example.data.model.*
import org.json.JSONArray
import org.json.JSONObject

object SampleData {

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

    fun getSampleReviewIssues(): List<ReviewIssue> {
        return listOf(
            ReviewIssue(
                id = 1,
                title = "عنوان نامشخص (دمو)",
                desc = "این بخش ممکن است عنوان فصل یا ادامه متن قبلی باشد.",
                original = "کاربردها\nاین فصل به بررسی کاربردهای عملی کتاب‌خوان می‌پردازد...",
                converted = "۱ـ۲ کاربردها",
                isResolved = false
            ),
            ReviewIssue(
                id = 2,
                title = "اتصال پاورقی نیازمند تأیید (دمو)",
                desc = "پاورقی ۱ ممکن است به پاراگراف اشتباهی متصل شده باشد.",
                original = "...که در استاندارد متنی تعریف شده است.¹\n\n¹ منبع: راهنمای ساختار کتاب الکترونیک",
                converted = "...که در استاندارد متنی تعریف شده است. [پاورقی ۱]",
                isResolved = false
            )
        )
    }

    fun getSampleBooks(): List<BookEntity> {
        val guideChapters = listOf(
            Chapter(
                id = "c1",
                title = "فصل اول: راهنمای آغازین و امکانات",
                level = 0,
                confident = true,
                content = """به سامانه کتاب‌خوان خوش آمدید. این نرم‌افزار به صورت کاملاً بومی و مستقل طراحی شده تا تجربه‌ای روان، آرام‌بخش و باکیفیت از مطالعه متون و ساختاردهی اسناد را برای شما فراهم آورد.

تمامی عملیات پردازش، ذخیره‌سازی و مدیریت اسناد به صورت آفلاین بر روی دستگاه شما انجام می‌گیرد. شما می‌توانید اندازه قلم، فاصله خطوط، جهت متن و پوسته‌های مختلف نوری یا شبانه را متناسب با نیاز خود تنظیم نمایید.

هدف این محیط، فراهم‌سازی بستری پاکیزه بدون هرگونه شلوغی بصری و با تمرکز صددرصدی بر محتوای متنی است.""",
                footnotes = listOf(
                    Footnote(id = "fn1", number = "۱", text = "تنظیمات مطالعه از طریق دکمه گوشه بالای صفحه در دسترس است."),
                    Footnote(id = "fn2", number = "۲", text = "تمامی داده‌ها در پایگاه داده محلی ذخیره می‌شوند.")
                ),
                imageCaption = "نمودار ۱-۱: معماری کتابخانه محلی و تجربه مطالعه آفلاین"
            ),
            Chapter(
                id = "c2",
                title = "فصل دوم: سفارشی‌سازی قلم و خوانایی",
                level = 0,
                confident = true,
                content = """خوانایی متن یکی از مهم‌ترین اصول در تجربه کاربری کتاب‌خوان است. انتخاب قلم مناسب، ترازبندی خطوط و ایجاد تضاد رنگی چشم‌نواز به ماندگاری تمرکز در زمان‌های طولانی مطالعه کمک می‌کند.

پوسته شب با نور ملایم گرم برای محافظت از چشم در محیط‌های کم‌نور طراحی شده است. شما می‌توانید در هر زمان با لمس صفحه، نوار کنترل مطالعه را باز یا پنهان کنید.""",
                footnotes = listOf(
                    Footnote(id = "fn3", number = "۱", text = "حالت شب از تم گرم برای کاهش خستگی چشم استفاده می‌کند.")
                )
            ),
            Chapter(
                id = "c3",
                title = "فصل سوم: مدیریت نشانک‌ها و فهرست",
                level = 0,
                confident = true,
                content = """با استفاده از قابلیت نشانک‌گذاری، می‌توانید موقعیت صفحه و فرازهای برگزیده را ثبت کرده و در مراجعات بعدی از طریق صفحه فهرست و نشانک‌ها به سادگی به همان نقطه بازگردید.

در این نسخه آزمایشی، ساختار فصول به همراه پاورقی‌ها به شکل تعاملی و پیوسته نمایش داده می‌شوند.""",
                footnotes = emptyList()
            )
        )

        val literatureChapters = listOf(
            Chapter(
                id = "lit1",
                title = "دیباچه: گزیده حکمت‌های ادب کهن",
                level = 0,
                confident = true,
                content = """سپاس و ستایش مر خدای را عز و جل که طاعتش موجب قربت است و به شکر اندرش مزید نعمت. هر نفسی که فرو می‌رود ممد حیات است و چون برمی‌آید مفرح ذات؛ پس در هر نفسی دو نعمت موجود است و بر هر نعمتی شکری واجب.

از دست و زبان که برآید
کز عهده شکرش به در آید?

بندگان همان به که ز تقصیر خویش
عذر به درگاه خدای آورند
ورنه سزاوار خداوندیش
کس نتواند که به جای آورد.""",
                footnotes = listOf(
                    Footnote(id = "lit_fn1", number = "۱", text = "متن دیباچه از گلستان سعدی شیرازی.")
                )
            ),
            Chapter(
                id = "lit2",
                title = "باب اول: در سیرت پادشاهان",
                level = 0,
                confident = true,
                content = """پادشاهی را شنیدم به کشتن اسیری اشارت کرد. بیچاره در حالت نومیدی به زبانی که داشت مَلِک را دشنام همی‌داد و سقط گفتن گرفت، که گفته‌اند: هر که دست از جان بشوید، هر چه در دل دارد بگوید. وقت ضرورت چو نماند گریز، دست بگیرد سر شمشیر تیز...""",
                footnotes = emptyList()
            )
        )

        val structureChapters = listOf(
            Chapter(
                id = "st1",
                title = "فصل اول: تحلیل ساختار فصل‌بندی",
                level = 0,
                confident = true,
                content = """در پردازش اسناد متنی، استخراج سلسله‌مراتب عنوان‌ها و پاراگراف‌ها گام نخستین در آماده‌سازی سند برای خوانش روان است. سند استاندارد شامل تیترهای اصلی، فرعی، پاراگراف‌های بدنه و ارجاعات پاورقی است.

این کتاب نمونه نحوه سازمان‌دهی فصل‌ها و فرآیند بازبینی ساختار را به شکل آزمایشی به نمایش می‌گذارد.""",
                footnotes = listOf(
                    Footnote(id = "st_fn1", number = "۱", text = "الگوی آزمایشی بررسی ساختار سند.")
                )
            ),
            Chapter(
                id = "st2",
                title = "فصل دوم: ارزیابی ارجاعات و تصاویر",
                level = 0,
                confident = false,
                content = """شناسایی خودکار پاورقی‌ها و پیوند دادن آن‌ها به متن اصلی سبب می‌شود تا خواننده بدون از دست دادن رشته کلام، مفهوم توضیحات را در پنجره شناور پایینی مشاهده کند.""",
                footnotes = emptyList()
            )
        )

        val sampleFourChapters = listOf(
            Chapter(
                id = "s4_1",
                title = "فصل اول: پیش‌نمایش سند ساختاریافته",
                level = 0,
                confident = true,
                content = """این کتاب یک نمونه آزمایشی برای ارزیابی وضعیت‌های گوناگون کتابخانه از قبیل وضعیت در حال پردازش، پیش‌نمایش نهایی و گزارش اشکالات است.

تمامی داده‌های این بخش جنبه نمایشی داشته و با برچسب دمو علامت‌گذاری شده‌اند.""",
                footnotes = emptyList()
            )
        )

        return listOf(
            BookEntity(
                id = "1",
                title = "راهنمای مطالعه دیجیتال (نسخه دمو)",
                author = "تیم توسعه کتاب‌خوان",
                translator = "",
                publisher = "پروژه کتاب‌خوان",
                publishYear = "۱۴۰۳",
                progress = 35,
                status = BookStatus.READING.name,
                coverColor = "#2B5329",
                coverAccent = "#4A7C47",
                lastRead = "امروز، ۱۰:۳۰",
                addedDate = "۱۴۰۳/۰۵/۱۰",
                chaptersCount = 3,
                currentChapterIndex = 0,
                currentScrollOffset = 0,
                isFavorite = true,
                chaptersJson = chaptersToJson(guideChapters)
            ),
            BookEntity(
                id = "2",
                title = "گزیده متون کهن فارسی (نمونه دمو)",
                author = "گلستان (متن کهن عمومی)",
                translator = "",
                publisher = "نشر آزمایشی",
                publishYear = "۱۴۰۲",
                progress = 100,
                status = BookStatus.READY.name,
                coverColor = "#5C3D2E",
                coverAccent = "#8B6350",
                lastRead = "دیروز، ۱۹:۲۰",
                addedDate = "۱۴۰۳/۰۵/۰۲",
                chaptersCount = 2,
                currentChapterIndex = 0,
                currentScrollOffset = 0,
                isFavorite = true,
                chaptersJson = chaptersToJson(literatureChapters)
            ),
            BookEntity(
                id = "3",
                title = "مبانی ساختار کتاب الکترونیک (دمو)",
                author = "بخش پژوهش محتوا",
                translator = "",
                publisher = "واحد فنی",
                publishYear = "۱۴۰۳",
                progress = 0,
                status = BookStatus.REVIEW.name,
                coverColor = "#2E4A5C",
                coverAccent = "#50788B",
                lastRead = null,
                addedDate = "۱۴۰۳/۰۵/۱۲",
                chaptersCount = 2,
                currentChapterIndex = 0,
                currentScrollOffset = 0,
                isFavorite = false,
                chaptersJson = chaptersToJson(structureChapters)
            ),
            BookEntity(
                id = "4",
                title = "نمونه بررسی ساختار متن (آزمایشی)",
                author = "سامانه پردازش آزمایشی",
                translator = "",
                publisher = "محیط دمو",
                publishYear = "۱۴۰۳",
                progress = 0,
                status = BookStatus.PROCESSING.name,
                coverColor = "#4A3D5C",
                coverAccent = "#75608B",
                lastRead = null,
                addedDate = "۱۴۰۳/۰۵/۱۴",
                chaptersCount = 1,
                currentChapterIndex = 0,
                currentScrollOffset = 0,
                isFavorite = false,
                chaptersJson = chaptersToJson(sampleFourChapters)
            )
        )
    }

    fun getSampleBookmarks(): List<BookmarkEntity> {
        return listOf(
            BookmarkEntity(
                id = 1,
                bookId = "1",
                chapterTitle = "فصل اول: راهنمای آغازین و امکانات",
                excerpt = "به سامانه کتاب‌خوان خوش آمدید. این نرم‌افزار به صورت کاملاً بومی...",
                date = "امروز، ۱۰:۳۰",
                chapterIndex = 0,
                scrollOffset = 50
            ),
            BookmarkEntity(
                id = 2,
                bookId = "1",
                chapterTitle = "فصل دوم: سفارشی‌سازی قلم و خوانایی",
                excerpt = "خوانایی متن یکی از مهم‌ترین اصول در تجربه کاربری کتاب‌خوان است...",
                date = "دیروز، ۱۶:۱۵",
                chapterIndex = 1,
                scrollOffset = 120
            ),
            BookmarkEntity(
                id = 3,
                bookId = "2",
                chapterTitle = "دیباچه: گزیده حکمت‌های ادب کهن",
                excerpt = "سپاس و ستایش مر خدای را عز و جل که طاعتش موجب قربت است...",
                date = "۳ روز پیش",
                chapterIndex = 0,
                scrollOffset = 30
            )
        )
    }
}
