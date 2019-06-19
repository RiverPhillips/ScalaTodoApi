package co.uk.riverphillips.domain

import java.util.Date

case class Todo(
                 val id: Int,
                 val name: String,
                 deadline: Date,
                 complete: Boolean
               )
