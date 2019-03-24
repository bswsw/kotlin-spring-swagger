package com.baegoony.springswaggerdemo.controller

import com.baegoony.springswaggerdemo.BaseControllerTest
import com.baegoony.springswaggerdemo.domain.team.Team
import com.baegoony.springswaggerdemo.domain.team.TeamRepository
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.MediaTypes
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.Date


class TeamControllerTest : BaseControllerTest() {

    @Autowired
    private lateinit var teamRepository: TeamRepository


    @Test
    fun create_OK() {
        // given
        val body = mapOf(
            "name" to "가나팀",
            "maker" to "아디다스",
            "year" to 2000
        )

        // when
        val result = this.mockMvc.perform(
            post("/teams")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON_UTF8)
                .content(this.objectMapper.writeValueAsBytes(body))
        )

        // then
        result
            .andExpect(status().isCreated)
            .andExpect(jsonPath("name").value(body.getValue("name")))
            .andExpect(jsonPath("maker").value(body.getValue("maker")))
            .andExpect(jsonPath("year").value(body.getValue("year")))
            .andDo(print())

    }

    @Test
    fun list_OK() {
        // given
        repeat(10) {
            this.generate()
        }

        // when
        val result = this.mockMvc.perform(
            get("/teams")
                .param("page", "2")
                .param("size", "2")
        )

        // then
        result
            .andExpect(status().isOk)
            .andDo(print())
    }

    private fun generate(): Team {
        val time = Date().time
        return this.teamRepository.save(
            Team(
                name = "name $time",
                maker = "bugs $time",
                year = 2019
            )
        )
    }
}
