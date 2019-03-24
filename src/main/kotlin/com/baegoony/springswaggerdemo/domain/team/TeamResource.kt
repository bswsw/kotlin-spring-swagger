package com.baegoony.springswaggerdemo.domain.team

import com.baegoony.springswaggerdemo.controller.TeamController
import org.springframework.hateoas.Resource
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo

class TeamResource(team: Team) : Resource<Team>(team) {

    init {
        this.add(linkTo(TeamController::class.java).slash(team.id).withSelfRel())
        this.add(linkTo(TeamController::class.java).withRel("list"))
    }
}
