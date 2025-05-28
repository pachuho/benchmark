package com.pachuho.benchmark.core.domain.error

import com.pachuho.benchmark.core.domain.R

object ErrorConstants {
    // Network
    val NETWORK_DISCONNECTED = R.string.error_message_network_disconnected
    val TIMEOUT = R.string.error_message_timeout
    val UNAUTHORIZED = R.string.error_message_unauthorized
    val NOT_FOUND = R.string.error_message_not_found
    val SERVER_ERROR = R.string.error_message_server_error
    val UNKNOWN_HOST = R.string.error_message_unknown_host

    // Application
    val JSON_PARSING = R.string.error_message_json_parsing
    val TOKEN_EXPIRED = R.string.error_message_token_expired
    val FIRE_TOKEN_NOT_FOUND = R.string.error_message_fcm_token_not_found
    val PERMISSION_DENIED = R.string.error_message_permission_denied
    val FILE_NOT_FOUND = R.string.error_message_file_not_found
    val USER_CANCELED = R.string.error_message_user_canceled
    val FEATURE_NOT_SUPPORTED = R.string.error_message_feature_not_supported
    val EMPTY_ID = R.string.error_message_id_empty
    val EMPTY_PASSWORD = R.string.error_message_password_empty
    val EMPTY_INPUT_TEXT = R.string.error_message_empty_input_test

    val UNKNOWN = R.string.error_message_unknown
}