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
    return jsonify({"message": "Email sent!"}), 200


@app.route("/API/message", methods=['POST'])
def sendMsgToAi():
    
    username = request.form.get('username')
    password = request.form.get('password')
    message = request.form.get('message')

    
    print(f"Received message from {username}: {message}")

# Process the received data and respond
    response_data = {
        "username": username,
        "password": password,
        "message": message,
        "status": "Received"
    }
    response_msg = message
    
    
    
    return jsonify(response_msg), 200




if __name__ == "__main__":
    app.run(debug=True)
