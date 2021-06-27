# OrangeOCSSample

# Application architecture:
![Applicaion architecture](https://user.oc-static.com/upload/2018/03/13/15209311930352_final-architecture.png)


# Application architecture explained:
<p align="center">
  <img src="https://google-developer-training.github.io/android-developer-advanced-course-concepts/images/14-1-c-architecture-components/dg_architecture_comonents.png">
</p>

# Clean architecture layers:

![clean archi layers](https://miro.medium.com/max/1888/1*vcnYWWn_zhNk6I30meBaPg.png)

**Presentation Layer** contains UI (Activities & Fragments) that are coordinated by Presenters/ViewModels which execute 1 or multiple Use cases. 

**Presentation Layer** depends on Domain Layer.

**Domain Layer** is the most INNER part of the onion (no dependencies with other layers) and it contains Entities, Use cases & Repository Interfaces. Use cases combine data from 1 or multiple Repository Interfaces.
Data Layer contains Repository Implementations and 1 or multiple Data Sources. Repositories are responsible to coordinate data from the different Data Sources. Data Layer depends on Domain Layer.


# Dependencies layers:

![layers dependencies](https://miro.medium.com/max/700/1*twBQBXvePT8eO7FbYcdzTg.png)

# Repository Pattern Design:

![Repository Pattern Design](https://miro.medium.com/max/2260/1*xxr1Idc8UoNELOzqXcJnag.png)

**The above design allow to avoid the bellow mistakes:**

- The Repository returns a DTO instead of a Domain Model.
- DataSources (ApiServices, Daos..) use the same DTO.
- There is a Repository per set of endpoints and not per Entity (or Aggregate Root if you like DDD).
- The Repository caches the whole model, even those fields that need to be always up to date.
- A DataSource is used by more than one Repository.






