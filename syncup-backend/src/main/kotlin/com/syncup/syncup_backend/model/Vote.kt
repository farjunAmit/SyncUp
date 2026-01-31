package com.syncup.syncup_backend.model


enum class Vote {

    /** User is fully available for the selected time slot */
    YES,

    /** User is possibly available, but prefers not to */
    YES_BUT,

    /** User is not available for the selected time slot */
    NO,
}