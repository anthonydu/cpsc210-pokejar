package model

import java.util.ArrayList
import java.util.List

/**
 * A Box that holds an arbitrary number of Pokemon.
 *
 * @author Anthony Du
 */
class Box : ArrayList<Pokemon> {

    /**
     * Constructs an empty Box.
     */
    constructor() : super()

    /**
     * Constructs a Box with an existing list of Pokemon.
     *
     * @param pokemons a list of Pokemon to initialize the Box
     */
    constructor(pokemons: List<Pokemon>) : super() {
        this.addAll(pokemons)
    }

    override fun add(pokemon: Pokemon): Boolean {
        val b = super.add(pokemon)
        EventLog.getInstance().logEvent(Event("Pokemon ${pokemon.name} added to box."))
        return b
    }

    override fun remove(element: Pokemon): Boolean {
        val b = super.remove(element)
        if (b) {
            EventLog.getInstance().logEvent(Event("Pokemon ${element.name} removed from box."))
        }
        return b
    }

    override fun removeAt(index: Int): Pokemon {
        val p = super.removeAt(index)
        EventLog.getInstance().logEvent(Event("Pokemon ${p.name} removed from box."))
        return p
    }


    /**
     * Returns the Pokemon that equals to the Pokemon that is passed in.
     *
     * @param pokemon the Pokemon to get
     * @return the Pokemon that equals to the Pokemon that is passed in
     */
    fun get(pokemon: Pokemon): Pokemon? {
        for (p in this) {
            if (p == pokemon) return p
        }
        return null
    }

    /**
     * Generates a String that represents this Box.
     *
     * @return a String that represents this Box
     */
    override fun toString(): String {
        var result = ""
        for (i in 0 until this.size) {
            result += "$i ${this[i]}\n"
        }
        return result.trim()
    }
}
