import React from "react";
import axios from "axios";

class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            cards: [],
            id: 0,
            nameOnCard: '',
            cardNumber: '',
            cardLimit: '',
            balance: 0,
            errorList: new Array(3),
            errorMessage: ''
        }
    }

    componentDidMount() {
        axios.get("http://localhost:8080/api/credit-card")
            .then((response) => {
                this.setState({
                    cards: response.data,
                    id: 0,
                    nameOnCard: '',
                    cardNumber: '',
                    cardLimit: '',
                    balance: 0,
                    errorList: new Array(3),
                    errorMessage: ''
                })
            })
    }

    submit(event) {
        event.preventDefault();
        axios.post("http://localhost:8080/api/credit-card", {
            nameOnCard: this.state.nameOnCard,
            cardNumber: this.state.cardNumber,
            cardLimit: this.state.cardLimit
        }).then((response) => {
            this.componentDidMount();
        }).catch(err => {
            if (err.response.data.fieldsErrors !== undefined) {
                let errorMessages = []
                for (const error of err.response.data.fieldsErrors) {
                    errorMessages.push(error.message)
                }
                this.setState({
                    errorList: errorMessages
                })
            } else {
                this.setState({
                    errorList: '',
                    errorMessage: err.response.data.message
                })
            }
        })
    }

    render() {
        return (
            <div className="container">
                <br/><br/>
                {this.state.errorList.length > 0 &&
                    this.state.errorList.map(error =>
                        <center>
                            <tr>
                                <h6>
                                    <div className="red-text text-darken-2">{error}</div>
                                </h6>
                            </tr>
                        </center>
                    )
                }
                {this.state.errorMessage !== '' &&
                    <center>
                        <tr>
                            <h6>
                                <div className="red-text text-darken-2">{this.state.errorMessage}</div>
                            </h6>
                        </tr>
                    </center>
                }
                <div className="col s6">
                    <form onSubmit={(event => this.submit(event))}>
                        <div className="input-field col s12">
                            <i className="material-icons prefix">person</i>
                            <input onChange={(e => this.setState({nameOnCard: e.target.value}))}
                                   value={this.state.nameOnCard} type="text" id="autocomplete-input"
                                   className="autocomplete"/>
                            <label htmlFor="autocomplete-input">Name on Card</label>
                        </div>
                        <div className="input-field col s12">
                            <i className="material-icons prefix">credit_card</i>
                            <input onChange={(e => this.setState({cardNumber: e.target.value}))}
                                   value={this.state.cardNumber} type="text" id="autocomplete-input"
                                   className="autocomplete"/>
                            <label htmlFor="autocomplete-input">Card Number</label>
                        </div>
                        <div className="input-field col s12">
                            <i className="material-icons prefix">attach_money</i>
                            <input onChange={(e => this.setState({cardLimit: e.target.value}))}
                                   value={this.state.cardLimit} type="text" id="autocomplete-input"
                                   className="autocomplete"/>
                            <label htmlFor="autocomplete-input">Card Limit</label>
                        </div>
                        <button className="btn waves-effect waves-light right" type="submit" name="action">Add
                            <i className="material-icons right">send</i>
                        </button>
                    </form>
                </div>
                <br/><br/><br/><br/><br/><br/><br/><br/><br/>
                <div className="col s6">
                    <table>
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Card Number</th>
                            <th>Limit</th>
                            <th>Balance</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.cards.map(card =>
                                <tr key={card.id}>
                                    <td>{card.nameOnCard}</td>
                                    <td>{card.cardNumber}</td>
                                    <td>{card.cardLimit}</td>
                                    <td>{card.balance}</td>
                                </tr>
                            )
                        }
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }
}

export default App;