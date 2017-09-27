# kotlinsql
A kotlin library that allows abstracting away sql syntax and provides a typed/typesafe alternative that is still
close to the metal. It provides typesafe abstractions without taking away the sql syntax.

##features
* DDL written in Kotlin
* Mirrors most of SQL (some parts are incomplete)
* Automatic creation/updating of tables based on the DDL
* Typesafe columns
* Typesafe queries/statements. The system knows the columns, their sql types and their java types and will not allow
  compilation of incorrect queries.
* Column copying. Secondary keys can copy the type of the primary column (in a different table) and always have the type the same.
  Other attributes need to be specified again (UNIQUE, NULL/NOTNULL DEFAULT) etc.
* Columns in the database and in the Kotlin table definition don't need to match.
* Since 0.6: (Requires Kotlin-1.1M04) names for columns are taken from the delegate property
* Since 0.6: (Requires Kotlin-1.1M04) supports custom columns that allow automatic translation between the java type and the
  native type for the underlying column configuration. Custom types are also typesafe, even difference in generic parameters will 
  cause compilation issues.

What this library is not:
* An ORM

## Example
* As example of usage of this library have a look at https://github.com/pdvrieze/ProcessManager/blob/master/darwin-sql/src/main/kotlin/webauth.kt for a table definition.
* A usage example is found in https://github.com/pdvrieze/ProcessManager/blob/master/accountcommon/src/main/kotlin/uk/ac/bournemouth/darwin/accounts/accounts.kt

##TODO
Some features are not implemented yet
* Support column aliassing. The code responsible for creating/updating tables assumes that columns are unique.
* Joins (inner, outer, left, right)
* Inner queries
* Stable API: the API should be fairly stable, but the missing features (and extensibility) may require changes.
* User specified table version transitions
* Write more extensive tests (not in using projects)
* Automatic joins

##YMMV
Extension of the library has not really been considered sufficiently yet

##Out of scope
This is not really an ORM library, but virtual columns (that map to multiple sql columns) may be a possibility. Custom columns
that require multiple tables are at this point far removed.
