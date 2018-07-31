package kashish.com.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.bumptech.glide.Glide
import kashish.com.R
import kashish.com.models.Movie
import kashish.com.utils.Constants.Companion.CONTENT_MOVIE
import kashish.com.utils.Constants.Companion.IMAGE_URL_BASE_PATH
import kashish.com.utils.Constants.Companion.getGenre
import kashish.com.utils.DateUtils
import kashish.com.viewholders.MovieViewHolder
import kashish.com.viewholders.ProgressBarViewHolder
import kotlinx.android.synthetic.main.movie_single_item.view.*


/**
 * Created by Kashish on 30-07-2018.
 */
class MovieAdapter(private var movieList: List<Movie>) : Adapter<RecyclerView.ViewHolder>() {

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view: View
        mContext = parent.context

        when(viewType){
            CONTENT_MOVIE -> {
                view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.movie_single_item, parent, false)
                return MovieViewHolder(view)
            }

            else -> {
                view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.recycler_view_progress_loader, parent, false);
                return ProgressBarViewHolder(view);
            }

        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder.itemViewType){
            CONTENT_MOVIE -> {
                val movieViewHolder = holder as MovieViewHolder
                val movie: Movie = movieList.get(holder.adapterPosition)
                var movieType = "Genre: "
                val image_url = IMAGE_URL_BASE_PATH.plus(movie.posterPath)

                movieViewHolder.movieTitle.setText(movie.title)
                movieViewHolder.movieRating.setText("Rating: ".plus(movie.voteAverage.toString()))
                movieViewHolder.moviePopularity.setText("Popularity: ".plus(movie.popularity.toString()))
                movieViewHolder.movieReleaseDate.setText("Release date: ".plus(DateUtils.getStringDate(movie.releaseDate!!)))

                for (i in movie.genreIds!!) {
                    if (i == movie.genreIds!!.size-1) movieType += getGenre(i)
                    else movieType += getGenre(i) + ", "
                }

                movieViewHolder.itemView.single_item_movie_type.setText(movieType)
                Glide.with(mContext).load(image_url).into(movieViewHolder.moviePoster)
            }

        }

    }


    override fun getItemViewType(position: Int): Int {
        return movieList.get(position).contentType!!
    }

    override fun getItemCount(): Int = movieList.size
}