package io.github.xvelx.movieviewer.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import io.github.xvelx.movieviewer.R
import io.github.xvelx.movieviewer.common.BaseTest
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import org.junit.Test

class BindingAdaptersKtTest : BaseTest() {

    private companion object {
        const val POSTER_URL =
            "https://m.media-amazon.com/images/M/MV5BYmVlYTg3N2QtMWM2OS00YWQyLWI2M2MtMDc0ZjBkZjk1MTY3XkEyXkFqcGdeQXVyNTE1NjY5Mg@@._V1_SX300.jpg"

        const val NA = "N/A"
    }

    @RelaxedMockK
    lateinit var requestManager: RequestManager

    @RelaxedMockK
    lateinit var requestBuilder: RequestBuilder<Drawable>

    @RelaxedMockK
    lateinit var imageView: ImageView

    override fun setUp() {
        super.setUp()
        mockkStatic(Glide::class)
        every { Glide.with(imageView) } returns requestManager

        every { requestManager.load(any<String>()) } returns requestBuilder
        every { requestManager.load(any<Int>()) } returns requestBuilder
    }

    @Test
    fun testGlide_whenHttpUrlRequested_loadsIntoImageView() {
        every { requestBuilder.placeholder(any<Int>()) } returns requestBuilder

        setImageUrl(
            imageView,
            POSTER_URL
        )

        verify { requestManager.load(POSTER_URL) }
        verify { requestBuilder.into(imageView) }
        verify { requestBuilder.placeholder(R.drawable.place_holder_poster) }
    }

    @Test
    fun testGlide_whenPosterNotAvailable_loadsPlaceHolder() {
        setImageUrl(
            imageView,
            NA
        )

        verify { requestManager.load(R.drawable.place_holder_poster) }
        verify { requestBuilder.into(imageView) }
    }

    override fun tearDown() {
        super.tearDown()
        unmockkStatic(Glide::class)
    }
}