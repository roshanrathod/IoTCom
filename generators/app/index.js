'use strict';
var util = require('util');
var path = require('path');
var yeoman = require('yeoman-generator');
var chalk = require('chalk');
var yosay = require('yosay');





var iotGenerator = yeoman.generators.Base.extend({
    promptUser: function() {
        var done = this.async();
		
    // Have Yeoman greet the user.
    this.log(yosay(
      'Welcome to the communication framework generator for ' + chalk.red('IOT Devices') + '!'
    ));
	
		
	
	

    var prompts = [
	{
	  type: 'string',
      name: 'packageName',
      message: 'Please enter your existing *Package Name* (leave blank if not applicable):',
      default: ''
    },
	{
             type: 'list',	
            name: 'programmingLang',
            message: ' Which type of *programming language* would you like the code base in?',
            choices: [
                {
                    value: 'Java',
                    name: 'Java'
                },
                {
                    value: 'python',
                    name: 'python'
                },
               
            ],
            default: 'Java'
    },
		{
             type: 'list',
            name: 'protocol',
            message: 'Select a *communication protocol*?',
            choices: [
                {
                    value: 'HTTP',
                    name: 'RESTful HTTP(GET/POST)'
                },
                {
                    value: 'MQTT',
                    name: 'MQTT(Messages)'
                },
               
            ],
            default: 'HTTP'
        },
	   {	
		  type: 'string',
		  name: 'ServerAddress',
		  message: 'Please enter Server Address where you will receive the data(POST requests):',
		  default: ''
       },
	    {	
		  type: 'string',
		  name: 'key',
		  message: 'Please enter a *Secret Key* for the AES-128 Encryption process(this can be changed later in the generated file):',
		  default: ''
       },
	   {
		type: 'int',
		name: 'numofVariables',
		message: 'Enter Number of variables needed(Please enter an integer value only): ',
		default: 1
	    }];

	var variableList = [];
	var variableListWithDatatype = [];
	var variablesPrompts = function(self, times) {
		
	var var_quest =[ 
	{
		type: 'string',
		name: 'inputVariable',
		message: 'Please input your IoT Device *variable(data)*',
		default: 'ID'
	}];
	
  self.prompt(var_quest, function(props){
    
				times --;
			  variableListWithDatatype.push("String " + props.inputVariable);
			  variableList.push(props.inputVariable);
			 
			  
			  self.variables = self.variables + " static String " + props.inputVariable + "; \n";
			  if (times != 0){
				  self.constructor = self.constructor + "String " + props.inputVariable + ", ";
			  }
			  else{
				  self.constructor = self.constructor + "String " + props.inputVariable;
			  }
			  
			  
			   
			if (times > 0) {
			   variablesPrompts(self, times);
			  
			}
			else {
				self.constructor = self.constructor + ") { \n";
				
				for (var i = 0; i<variableList.length; i++){
					self.constructor = self.constructor + 'this.' +variableList[i] + ' = ' + variableList[i] + '; \n' ;
					self.putVariablesInMap = self.putVariablesInMap + 'data.put("' + variableList[i] + '", AES.encrypt(' + variableList[i] +')); \n' ;
				}
				
				self.constructor = self.constructor + "}"
				self.variablesWithDatatypes = variableListWithDatatype;
				
			 done();
			}
	
  });
}

		
    this.prompt(prompts, function (props) {
	
       this.ServerAddress = props.ServerAddress;
	   this.key = props.key;
	   this.variables = '';
	   this.packageName = '';
	   this.variablesWithDatatypes = '';
	   this.putVariablesInMap = '';
	   this.constructor = "postData(";
	   
	   if (props.packageName !== ''){
		   this.packageName = "package " + props.packageName + ";";
	   }
	   
	   //if(typeof props.numofVariables !== 'number'){
		//   props.numofVariables = 2;
	  // }
	  variablesPrompts(this, props.numofVariables);
	 
    }.bind(this));
  },

  
});
module.exports = iotGenerator;

iotGenerator.prototype.PostToServer = function PostToServer() {
  this.template('PostToServer.java');
};

iotGenerator.prototype.scaffolding = function scaffolding() {
	this.copy('PostToServer.java', 'PostToServer.java');
	this.copy('AES.java', 'AES.java');
	this.log('Great! You are all Set. All Files Generated!')
};
