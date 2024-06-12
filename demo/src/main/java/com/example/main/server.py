from flask import Flask, request, send_file, jsonify, send_from_directory
from flask_cors import CORS
import json
import os
import shutil



app = Flask(__name__)
CORS(app, resources={
    r"/API/Login": {"origins": ["https://findperfectemployee.azurewebsites.net","http://localhost:3000"]},
    r"/API/Login": {"origins": ["https://findperfectemployee.azurewebsites.net","http://localhost:3000"]}   
    }
    )


@app.route("/API/login", methods=['GET'])
def getFileFromApp():
    if request.form.get('Database'):
        
        
        token = "Fc(UQVJZa4"
        
        return jsonify({
            "message": "Poprawnie połączono",
            "status": "success",
            "Bearer": token
        }), 200
    else:
        response_error_message = {
    
            "message": "error during connection",
            "status": "error"}
        return jsonify(response_error_message), 400

@app.route("/API/message", methods=['POST'])
def sendMsgToAi():
    if request.form.get('Database') and request.form.get('message'):
        tablename = request.form.get('Database')
    
        message = request.form.get('message')
    
        
        print(f"Received message to table: {tablename}: {message}")
    
    
        
        response_msg = {
    
            "message": f"Otrzymałem wiadomość {message}",
            "status": "Received"
        }
        
        
        
        return jsonify(response_msg), 200
    else:
        response_error_message = {
    
            "message": "Invalid Data provided",
            "status": "error"}
        return jsonify(response_error_message), 400




if __name__ == "__main__":
    app.run(debug=True, port=5000)
