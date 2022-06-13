
import React, { Component } from 'react'
import {Link} from 'react-router-dom';
import {attemptLogin, attemptLogout} from '../../actions/searchActions'
import {connect} from 'react-redux' ;
import PropTypes from 'prop-types';
import './Header.css';

export class Header extends Component {
    
  constructor(props){
    super(props);
    this.state={
        loginBox: false,
        message: "",
        username: "",
        password: ""
    }
  }


    login = () =>{
        if(this.props.authentication.authenticated){return}
        const authenticationObject={
            authenticated: true,
            username:this.state.username,
            password:this.state.password
        }
        this.props.attemptLogin(authenticationObject, (success)=>{
            if(success) {
                this.showLogin(false)
            } else {
                this.setState({message:"Wrong Credentials"})
            }
        });
    }
    showLogin=(show)=>{
        this.setState({
            loginBox: show,
            message: ""
        })
    }
    logout(){
        if(!this.props.authentication.authenticated){return}
        const authenticationObject={
            authenticated: false,
            username:"",
            password:""
        }
        this.props.attemptLogout(authenticationObject);
        this.setState({
            loginBox: false
        })
    }
    headerStyle= {
        background: '#D3D3D3',
        padding: '5px'
    }

    render(){ 
        const loginBox = (
        <div id="overlay">
            <div id="overlay-box">
                <input type="text" placeholder="username" onChange={(e)=>this.setState({username: e.target.value, message:""})}></input>
                <input type="password" placeholder="password" onChange={(e)=>this.setState({password: e.target.value, message:""})}></input>
                <div className="btn btn-sm btn-secondary" onClick={() => this.login()}>Login</div>
                <div className="btn btn-sm btn-secondary" onClick={() => this.showLogin(false)}>Cancel</div>
                <div>{this.state.message}</div>
            </div>
        </div>)

        return(
        <header className=" border-bottom border-dark mb-3" style={this.headerStyle}>
            <div className="App ml-5 mr-5">
                <div className="row">
                    <div className="col-sm-9">
                        <Link className="mx-1 btn btn-sm btn-secondary" to='/'>Gallery</Link>
                        <Link className="btn btn-sm btn-secondary" to='/contact'>Contact</Link>
                        <span className="mx-1 text-secondary font-italic"> 718-366-6888 </span>
                        <span className="mx-1 text-secondary font-italic"> We've Moved!</span>
                    </div>
                    
                    <div className="col-sm-3 d-flex justify-content-end">
                        
                        {this.props.authentication.authenticated && 
                          (<Link   className="btn btn-sm btn-secondary mr-2" to='/inventory'>Inventory</Link> )
                        }
                        {this.props.authentication.authenticated?
                            <div className="btn btn-sm btn-secondary" onClick={() => this.logout()} >Logout</div>    
                            :
                            <div className="btn btn-sm btn-secondary" onClick={() => this.showLogin(true)} >Login</div>    
                        }
                        {this.state.loginBox && loginBox}
                        <span className="mx-1 text-secondary font-italic"> v.1.3</span>
                    </div>
                </div>
            </div>
        </header>
    )
}
}
export const linkStyle = {
    color: '#FFFF00'
}


Header.propTypes = {
    attemptLogin: PropTypes.func.isRequired,
    attemptLogout: PropTypes.func.isRequired,
}
  
  
  const mapStateToProps = (state) => {
    return { 
    authentication: state.searchReducer.authentication
    }
  };
  
  export default connect(mapStateToProps, {attemptLogin, attemptLogout} )(Header);
  
  
  
  