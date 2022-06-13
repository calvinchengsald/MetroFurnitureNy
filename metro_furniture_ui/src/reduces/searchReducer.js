import {actionTypes} from '../actions/types';

const initialState = {
    filterObject: {
        filterBaseCode: "",
        filterType: "",
        filterSubtype: "",
        filterTag: "",
        filterGeneral: "",
    },
    authentication: {
        authenticated: false,
        username: "",
        password: ""
    }
}

export default function( state=initialState, action) {
    switch(action.type) {
        case actionTypes.SEARCH_UDPATE :
            return {
                ...state,
                filterObject: action.payload,
            }
        case actionTypes.AUTH_UPDATE :
            return {
                ...state,
                authentication: action.payload,
            }
        default: return state;
    }
}




