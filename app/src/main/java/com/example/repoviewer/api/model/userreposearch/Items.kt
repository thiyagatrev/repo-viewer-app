package com.example.repoviewer.api.model.userreposearch

import com.google.gson.annotations.SerializedName

data class Items(

    @SerializedName("id") var id: Int,
    @SerializedName("node_id") var node_id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("full_name") var full_name: String? = null,
    @SerializedName("private") var private: Boolean,
    @SerializedName("owner") var owner: Owner,
    @SerializedName("html_url") var html_url: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("fork") var fork: Boolean,
    @SerializedName("url") var url: String? = null,
    @SerializedName("forks_url") var forks_url: String? = null,
    @SerializedName("keys_url") var keys_urls_url: String? = null,
    @SerializedName("collaborators_url") var collaborators_url: String? = null,
    @SerializedName("teams_url") var teams_url: String? = null,
    @SerializedName("hooks_url") var hooks_url: String? = null,
    @SerializedName("issue_events_url") var issue_events_url: String? = null,
    @SerializedName("events_url") var events_url: String? = null,
    @SerializedName("assignees_url") var assignees_url: String? = null,
    @SerializedName("branches_url") var branches_url: String? = null,
    @SerializedName("tags_url") var tags_url: String? = null,
    @SerializedName("blobs_url") var blobs_url: String? = null,
    @SerializedName("git_tags_url") var git_tags_url: String? = null,
    @SerializedName("git_refs_url") var git_refs_url: String? = null,
    @SerializedName("trees_url") var trees_url: String? = null,
    @SerializedName("statuses_url") var statuses_url: String? = null,
    @SerializedName("languages_url") var languages_url: String? = null,
    @SerializedName("stargazers_url") var stargazers_url: String? = null,
    @SerializedName("contributors_url") var contributors_url: String? = null,
    @SerializedName("subscribers_url") var subscribers_url: String? = null,
    @SerializedName("subscription_url") var subscription_url: String? = null,
    @SerializedName("commits_url") var commits_url: String? = null,
    @SerializedName("git_commits_url") var git_commits_url: String? = null,
    @SerializedName("comments_url") var comments_url: String? = null,
    @SerializedName("issue_comment_url") var issue_comment_url: String? = null,
    @SerializedName("contents_url") var contents_url: String? = null,
    @SerializedName("compare_url") var compare_url: String? = null,
    @SerializedName("merges_url") var merges_url: String? = null,
    @SerializedName("archive_url") var archive_url: String? = null,
    @SerializedName("downloads_url") var downloads_url: String? = null,
    @SerializedName("issues_url") var issues_url: String? = null,
    @SerializedName("pulls_url") var pulls_url: String? = null,
    @SerializedName("milestones_url") var milestones_url: String? = null,
    @SerializedName("notifications_url") var notifications_url: String? = null,
    @SerializedName("labels_url") var labels_url: String? = null,
    @SerializedName("releases_url") var releases_url: String? = null,
    @SerializedName("deployments_url") var deployments_url: String? = null,
    @SerializedName("created_at") var created_at: String? = null,
    @SerializedName("updated_at") var updated_at: String? = null,
    @SerializedName("pushed_at") var pushed_at: String? = null,
    @SerializedName("git_url") var git_url: String? = null,
    @SerializedName("ssh_url") var ssh_url: String? = null,
    @SerializedName("clone_url") var clone_url: String? = null,
    @SerializedName("svn_url") var svn_url: String? = null,
    @SerializedName("homepage") var homepage: String? = null,
    @SerializedName("size") var size: Int,
    @SerializedName("stargazers_count") var stargazers_count: Int,
    @SerializedName("watchers_count") var watchers_count: Int,
    @SerializedName("language") var language: String? = null,
    @SerializedName("has_issues") var has_issues: Boolean,
    @SerializedName("has_projects") var has_projects: Boolean,
    @SerializedName("has_downloads") var has_downloads: Boolean,
    @SerializedName("has_wiki") var has_wiki: Boolean,
    @SerializedName("has_pages") var has_pages: Boolean,
    @SerializedName("forks_count") var forks_count: Int,
    @SerializedName("mirror_url") var mirror_url: String? = null,
    @SerializedName("archived") var archived: Boolean,
    @SerializedName("disabled") var disabled: Boolean,
    @SerializedName("open_issues_count") var open_issues_count: Int,
    @SerializedName("forks") var forks: Int,
    @SerializedName("open_issues") var open_issues: Int,
    @SerializedName("watchers") var watchers: Int,
    @SerializedName("default_branch") var default_branch: String? = null,
    @SerializedName("score") var score: Int
)