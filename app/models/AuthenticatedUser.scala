package models

import models.Foursquare.Location


case class AuthenticatedUser( name: String,
                              vorName: String,
                              email: String,
                              dayOfBirth: Int,
                              monthOfBirth: Int,
                              yearOfBirth: Int,
                              presentation: Option[String],
                              town: Option[Location])
