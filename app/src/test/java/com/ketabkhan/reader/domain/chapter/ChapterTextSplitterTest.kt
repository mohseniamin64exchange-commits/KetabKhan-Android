package com.ketabkhan.reader.domain.chapter

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ChapterTextSplitterTest {

    private lateinit var splitter: ChapterTextSplitter

    @Before
    fun setUp() {
        splitter = ChapterTextSplitter(ChapterHeadingDetector())
    }

    @Test
    fun splitText_empty_or_null_text_returns_empty_result() {
        val nullResult = splitter.splitText(null)
        assertEquals("", nullResult.preamble)
        assertTrue(nullResult.chapters.isEmpty())

        val emptyResult = splitter.splitText("")
        assertEquals("", emptyResult.preamble)
        assertTrue(emptyResult.chapters.isEmpty())

        val blankResult = splitter.splitText("   \n\n  \t ")
        assertEquals("", blankResult.preamble)
        assertTrue(blankResult.chapters.isEmpty())
    }

    @Test
    fun splitText_no_headings_puts_all_text_in_preamble() {
        val rawText = """
            این یک متن ساده است.
            هیچ عنوان فصلی در این متن وجود ندارد.
            خط سوم از متن ساده.
        """.trimIndent()

        val result = splitter.splitText(rawText)
        assertEquals(rawText, result.preamble)
        assertTrue(result.chapters.isEmpty())
    }

    @Test
    fun splitText_with_preamble_and_multiple_chapters() {
        val rawText = """
            کتاب تستی
            نویسنده: تست کننده
            
            مقدمه
            این متن مقدمه کتاب است که قبل از فصل اول آمده است.
            
            فصل اول: آشنایی
            محتوای فصل اول اینجاست.
            توضیحات بیشتر در مورد فصل اول.
            
            فصل دوم: پیشرفته
            محتوای فصل دوم اینجاست.
            
            نتیجه‌گیری
            سخنان پایانی کتاب در اینجا قرار دارد.
        """.trimIndent()

        val result = splitter.splitText(rawText)

        assertEquals("کتاب تستی\nنویسنده: تست کننده", result.preamble)
        assertEquals(4, result.chapters.size)

        assertEquals("مقدمه", result.chapters[0].title)
        assertEquals("این متن مقدمه کتاب است که قبل از فصل اول آمده است.", result.chapters[0].content)

        assertEquals("فصل اول: آشنایی", result.chapters[1].title)
        assertEquals("محتوای فصل اول اینجاست.\nتوضیحات بیشتر در مورد فصل اول.", result.chapters[1].content)

        assertEquals("فصل دوم: پیشرفته", result.chapters[2].title)
        assertEquals("محتوای فصل دوم اینجاست.", result.chapters[2].content)

        assertEquals("نتیجه‌گیری", result.chapters[3].title)
        assertEquals("سخنان پایانی کتاب در اینجا قرار دارد.", result.chapters[3].content)
    }

    @Test
    fun splitText_starting_directly_with_chapter_heading() {
        val rawText = """
            فصل ۱
            متن اولین بخش کتاب بدون هیچ پیش‌گفتاری.
            
            فصل ۲
            متن دومین بخش.
        """.trimIndent()

        val result = splitter.splitText(rawText)

        assertEquals("", result.preamble)
        assertEquals(2, result.chapters.size)

        assertEquals("فصل ۱", result.chapters[0].title)
        assertEquals("متن اولین بخش کتاب بدون هیچ پیش‌گفتاری.", result.chapters[0].content)

        assertEquals("فصل ۲", result.chapters[1].title)
        assertEquals("متن دومین بخش.", result.chapters[1].content)
    }
}
