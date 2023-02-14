# Observer

The `Observer` can be used to mark a property as observable. With this you can dynamically update the UI when the property changes.
Most setter or constructors can either be used with a direct value or a `KProperty0` which is a reference to a property.
When using the latter in conjunction with a backing `Observer`, value changed propagate to the UI.

## Default Observer
- `::True`
- `::False`
- `::Zero`
- `::ZeroLong`
- `::ZeroFloat`
- `::ZeroDouble`
- `::EmptyString`
- `::Time`, will be updated every 1000/60 milliseconds
- `::TimeSeconds`, will be updated every 1000/60 milliseconds
- `::TimeDelta`, will be updated every 1000/60 milliseconds
- `::TimeDeltaSeconds`, will be updated every 1000/60 milliseconds

## Creating a simple observer
```kotlin
var a by Observer(0)
var b by Observer(3)
```

You can reference a property with `::` and the name of the property.

## Referencing other properties
### Example 1
```kotlin
var a by Observer(3)
var square by Observer(::a) { it * it }
```

### Example 2
```kotlin
var a by Observer(3)
var b by Observer(4)
var area by Observer(::a, ::b) { a, b -> a * b }
```

If you have more than 5 properties the rest will be passed as a vararg Array and the lambda will be called with a List of all passed property values.
