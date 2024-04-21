package mod

class Vector3d(
    var x: Double = 0.0,
    var y: Double = 0.0,
    var z: Double = 0.0
)
{
    fun subtract(other : Vector3d) : Vector3d
    {
        return Vector3d(x - other.x, y - other.y, z - other.z)
    }
}