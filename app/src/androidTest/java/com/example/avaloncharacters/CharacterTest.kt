package com.example.avaloncharacters

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.avaloncharacters.characters.Character
import com.example.avaloncharacters.characters.evilside.EvilSide
import com.example.avaloncharacters.characters.goodside.GoodSide
import com.example.avaloncharacters.characters.goodside.LoyalServantOfArthur
import com.example.avaloncharacters.characters.goodside.Merlin
import com.example.avaloncharacters.characters.goodside.Percival
import org.hamcrest.Matchers
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.reflect.KFunction
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible


@RunWith(AndroidJUnit4::class)
@SmallTest
class CharacterTest {
    private val targetContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun inheritanceCheck() {
        val merlin: Character = Merlin(targetContext)

        assertEquals(merlin is Merlin, true)
        assertEquals(merlin is GoodSide, true)

        assertEquals(merlin is Percival, false)
        assertEquals(merlin is LoyalServantOfArthur, false)
        assertEquals(merlin is EvilSide, false)
    }

    @Test
    fun characterFactoryValidNamesMatchThoseInXML() {
        val ctorMapKeys = Character.Companion::class.memberProperties
            .find {
                it.name == "constructors"
            }!!.run {
            isAccessible = true
            get(Character.Companion) as HashMap<String, KFunction<Character>>
        }.keys

        val namesInXml =
            targetContext.resources.getStringArray(R.array.avalon_characters)

        for (name in namesInXml) {
            assertThat(
                "CharacterFactory's constructor hash map should contain name: $name",
                ctorMapKeys,
                Matchers.hasItem(name)
            )

            assertThat(
                "CharacterFactory's ValidCharacterName should contain name: $name",
                Character.ValidCharacterName,
                Matchers.hasItem(name)
            )
        }
    }

    @Test
    fun reflectionClassCreation() {
        val merlinString = targetContext.getString(R.string.avalon_Merlin)
        val merlinInstance = Character.newInstance(merlinString, targetContext)

        assertTrue(merlinInstance is Merlin)
    }
}