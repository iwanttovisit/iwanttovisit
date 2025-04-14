# I Want To Visit

**IWantToVisit** - is where you can add places on map and don't forget about it.

**Content:**

* [How to run this project](#how-to-run-this-project)
* [Entities](#entities)
    * [User](#user)
    * [Map](#map)
    * [Place](#place)
    * [Review](#review)
* [API](#api)
* [Contribution](#contribution)

## How to run this project

To run this project you need to clone this project to your device.

We use Java 17 and Maven, so you need to install it (if you don't have it
already).

We use Docker to manage dependencies like Postgresql.

After cloning create `.env` file in root folder of repository and add your
credentials. You can use `example.env` as example.

To start an app with its dependencies run this command:

```sh
docker compose up -d
```

## Entities

This app have some core entities.

All core entities extends `BaseEntity` - parent entity with common fields -
`id`, `status`, `created` and `updated` properties.

### User

`User` represents a user of an app. Users can register and login into
account to get access to their maps and places.

### Map

`Map` represents a set of places that user wants to visit.

These objects have title, description and author. It can be public or private (
by default).

### Place

`Place` represents a specific point on map that user wants to visit.

Place has name, description, category, geo coordinates, social network url,
rating and visited flag. After visiting user can add mark this place as visited
and hide it from results.

By now we have 5 categories:

* `CAFE` - cafes, restaurants, places to eat
* `HOBBY`- some places to do a hobby
* `SPORT` - sport palaces, stadiums
* `ENTARTAINMENT` - attractions, clubs
* `NATURE` - beautiful sides, forests and parks

### Review

`Review` represents user`s review about its places.

Review has text and mark that changes place's rating.

## API

We will add more information a bit later.

## Contribution

You can participate in developing of this project by creating a fork and opening
pull requests after issue is done.

You can see available issues
on [Issues](https://github.com/iwanttovisit/iwanttovisit/issues) page.

To avoid multiple requests changes statuses, follow common codestyle and ensure
that all checks are passing.
