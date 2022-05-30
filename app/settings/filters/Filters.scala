package settings.filters

import org.pac4j.play.filters.SecurityFilter
import play.api.http.HttpFilters
import play.api.mvc.EssentialFilter

import javax.inject.Inject

class Filters @Inject() (securityFilter: SecurityFilter) extends HttpFilters {
  override def filters: Seq[EssentialFilter] = Seq(securityFilter)
}
