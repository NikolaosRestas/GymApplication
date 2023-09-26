import {Component} from 'react';

class CountiesList extends Component {

    constructor(props) {
        super(props);
        this.state = {clients: []};
        this.remove = this.remove.bind(this);
    }

    async componentDidMount() {
        fetch('http://localhost:8080/counties')
            .then(response => response.json())
            .then(data => {
                this.setState({clients: data});
            });
    }
}

export default CountiesList;