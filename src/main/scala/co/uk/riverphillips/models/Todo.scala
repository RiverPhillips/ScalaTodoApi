package co.uk.riverphillips.models

import java.time.LocalDate

case class Todo(
                 name: String,
                 deadline: LocalDate,
                 complete: Boolean,
                 id: Option[Int] = null
               )


