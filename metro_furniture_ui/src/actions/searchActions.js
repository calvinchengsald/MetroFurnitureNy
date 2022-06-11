import {actionTypes} from './types'
import axios from 'axios';
import {basePath} from '../configurations/config';



// Search is used for the /search endpoint
// when filtering products by base_code/type/subtype/tags
export function changeSearch( filterObject) {

    return function(dispatch) {

        dispatch({
            type: actionTypes.SEARCH_UDPATE,
            payload: filterObject
        })
    }
    


}


export function attemptLogin( authenticationObject,successfulCallback) {

    return function(dispatch) {
        
        axios.post( basePath + '/authentication/login',authenticationObject )
        .then(res => {
           successfulCallback(res.data.content.authenticated);
           if(res.data.content.authenticated){
            dispatch({
                type: actionTypes.AUTH_UPDATE,
                payload: res.data.content
            })
           }
        })
        .catch( error => {
            console.log(error);
            console.log(error.response);
   
           dispatch({
               type: actionTypes.MESSAGE_CHANGE,
               payload: error
           })
        })

   }

}


export function attemptLogout( authenticationObject) {
    return function(dispatch) {
        dispatch({
            type: actionTypes.AUTH_UPDATE,
            payload: authenticationObject
        })
    }
}
