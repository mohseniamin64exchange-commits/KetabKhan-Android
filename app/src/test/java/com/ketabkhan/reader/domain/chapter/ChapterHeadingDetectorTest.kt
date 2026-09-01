package com.ketabkhan.reader.domain.chapter

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ChapterHeadingDetectorTest {

    private lateinit var detector: ChapterHeadingDetector

    @Before
    fun setUp() {
        detector = ChapterHeadingDetector()
    }

    @Test
    fun isHeading_detects_standard_persian_chapter_patterns() {
        assertTrue(detector.isHeading("فصل اول"))
        assertTrue(detector.isHeading("فصل دوم"))
        assertTrue(detector.isHeading("فصل ۱"))
        assertTrue(detector.isHeading("فصل ۲: مبانی و تعاریف"))
        assertTrue(detector.isHeading("فصل دهم - بررسی الگوها"))
        assertTrue(detector.isHeading("بخش اول"))
        assertTrue(detector.isHeading("بخش ۳: نتیجه آزمایش‌ها"))
        assertTrue(detector.isHeading("گفتار نخست"))
    }

    @Test
    fun isHeading_detects_standalone_special_headings() {
        assertTrue(detector.isHeading("مقدمه"))
        assertTrue(detector.isHeading("پیشگفتار"))
        assertTrue(detector.isHeading("پیش گفتار"))
        assertTrue(detector.isHeading("نتیجه گیری"))
        assertTrue(detector.isHeading("نتیجه‌گیری"))
        assertTrue(detector.isHeading("جمع بندی"))
        assertTrue(detector.isHeading("جمع‌بندی"))
        assertTrue(detector.isHeading("منابع"))
        assertTrue(detector.isHeading("منابع و مآخذ"))
        assertTrue(detector.isHeading("ضمیمه"))
        assertTrue(detector.isHeading("ضمائم"))
        assertTrue(detector.isHeading("پیوست"))
        assertTrue(detector.isHeading("مقدمه:"))
    }

    @Test
    fun isHeading_rejects_empty_and_blank_lines() {
        assertFalse(detector.isHeading(null))
        assertFalse(detector.isHeading(""))
        assertFalse(detector.isHeading("   "))
        assertFalse(detector.isHeading("\n\t"))
    }

    @Test
    fun isHeading_rejects_long_paragraphs() {
        val longText = "فصل اول یکی از مهم‌ترین بخش‌های این کتاب به شمار می‌رود که در آن نویسنده به تفصیل درباره سیر تحولات تاریخی و فلسفی موضوع به بررسی پرداخته است و در ادامه توضیحات فراوانی در مورد سایر جنبه‌ها ارائه می‌دهد و این متن ادامه دارد."
        assertFalse(detector.isHeading(longText))
    }

    @Test
    fun isHeading_rejects_sentences_with_chapter_word_in_the_middle() {
        assertFalse(detector.isHeading("در این فصل اول از کتاب می‌خوانیم"))
        assertFalse(detector.isHeading("کتاب شامل چندین فصل مختلف است که هر کدام موضوعی را بیان می‌کنند."))
        assertFalse(detector.isHeading("این بخش بسیار جذاب بود و نکات خوبی داشت."))
    }
}
