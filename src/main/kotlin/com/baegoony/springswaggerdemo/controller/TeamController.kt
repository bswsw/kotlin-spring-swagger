package com.baegoony.springswaggerdemo.controller

import com.baegoony.springswaggerdemo.domain.team.Team
import com.baegoony.springswaggerdemo.domain.team.TeamBody
import com.baegoony.springswaggerdemo.domain.team.TeamParams
import com.baegoony.springswaggerdemo.domain.team.TeamRepository
import com.baegoony.springswaggerdemo.domain.team.TeamResource
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.modelmapper.ModelMapper
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.MediaTypes
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.annotations.ApiIgnore
import java.net.URI
import javax.validation.Valid

@Api(description = "팀 관리 API")
@RestController
@RequestMapping("/teams")
class TeamController(
    private val teamRepository: TeamRepository,
    private val modelMapper: ModelMapper
) {

    @ApiOperation("팀 생성 API", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    @ApiResponses(
        ApiResponse(code = 201, message = "생성완료"),
        ApiResponse(code = 400, message = "요청 똑바로 해라"),
        ApiResponse(code = 500, message = "서버가 맛탱이 갔음")
    )
    @PostMapping
    fun create(@RequestBody @Valid body: TeamBody): ResponseEntity<TeamResource> {
        val team = this.modelMapper.map(body, Team::class.java)
        val savedTeam = this.teamRepository.save(team)
        val resource = TeamResource(savedTeam)

        return ResponseEntity.created(URI(resource.id.href)).body(resource)
    }

    @ApiOperation("팀 목록 API", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    @ApiResponses(
        ApiResponse(code = 200, message = "목록 조회 완료"),
        ApiResponse(code = 400, message = "요청 이상"),
        ApiResponse(code = 500, message = "서버 다이")
    )
    @ApiImplicitParams(
        ApiImplicitParam("페이지 번호", name = "page", dataType = "int", paramType = "query", defaultValue = "0"),
        ApiImplicitParam("페이지 사이즈", name = "size", dataType = "int", paramType = "query", defaultValue = "20"),
        ApiImplicitParam("정렬", name = "sort", dataType = "string", paramType = "query")
    )
    @GetMapping
    fun list(
        @ModelAttribute params: TeamParams,
        @ApiIgnore pageable: Pageable,
        @ApiIgnore pagedResourcesAssembler: PagedResourcesAssembler<Team>
    ): ResponseEntity<*> {
        val teams = this.teamRepository.findAll(pageable)

        return ResponseEntity.ok(
            pagedResourcesAssembler.toResource(
                teams,
                ::TeamResource
            )
        )
    }
}

