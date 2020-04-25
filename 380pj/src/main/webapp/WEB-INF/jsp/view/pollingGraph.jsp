<script>
    var config = {
    type: 'doughnut',
            data: {
            datasets: [{
            data: [
    ${polling.getOptionNumber(1)}
    ${empty polling.option2 ? '' : ','} ${empty polling.option2 ? '' : polling.getOptionNumber(2)}
    ${empty polling.option3  ? '' : ','} ${empty polling.option3  ? '' : polling.getOptionNumber(3)}
    ${empty polling.option4  ? '' : ','} ${empty polling.option4  ? '' : polling.getOptionNumber(4)}
            ],
                    backgroundColor: [
                            'grey', 'orange', 'red', 'blue'
                    ],
                    label: 'result'
            }],
                    labels: [
                            "${polling.option1}"
    ${empty polling.option2 ? '' :',"'+=polling.option2+='"'}
    ${empty polling.option3 ? '' :',"'+=polling.option3+='"'}
    ${empty polling.option4 ? '' :',"'+=polling.option4+='"'}
                    ]
            },
            options: {
            maintainAspectRatio: false
            }
    };
    
    var ctx = document.getElementById('pollingGraph_${polling.title}').getContext('2d');
    window.myPie = new Chart(ctx, config);
    
</script>