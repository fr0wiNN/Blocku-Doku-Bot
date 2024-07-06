import zlib
from flask import Flask, request, jsonify

import os
import subprocess  # Import the subprocess module
from datetime import datetime

app = Flask(__name__)

@app.route('/upload', methods=['POST'])
def upload_image():
    print("--- Request Detected ---")
    if not request.data:
        return jsonify({"error": "No data provided"}), 400
    
    timestamp = datetime.now().strftime("%Y%m%d%H%M%S")
    filename = f"{timestamp}.png"
    save_directory = 'Server/Server_Img'
    if not os.path.exists(save_directory):
        os.makedirs(save_directory)
    filepath = os.path.join(save_directory, filename)

    # Save the incoming image data to a file
    with open(filepath, 'wb') as f:
        request.data = request.data
        f.write(zlib.decompress(request.data))
        #print(f"Saving image {filename}")

    try:
        # Execute the Java command and capture the output
        command = f"java -jar .\\Blocku-Doku-Bot.jar {filepath}"
        result = subprocess.run(command, shell=True, capture_output=True, text=True, check=True)
        output = result.stdout.strip()
        #print(f"Executed command: {command}")
        print(f"Output: {output}")
    except subprocess.CalledProcessError as e:
        print(f"Failed to run command: {e}")
        return jsonify({"error": "Failed to process image"}), 500

    # Parse the output to return as JSON
    coordinatesPart = output.split("XXXXX")
    # print(coordinatesPart)
    coordinates = coordinatesPart[1].split()  # Assuming the output is space-separated values
    # Assuming these coordinates are pairs
    coordinates = list(map(int, coordinates))  # Convert to integers if needed

    print(coordinates)

    return jsonify({"message": "Image successfully uploaded and processed", "filename": filename, "coordinates": coordinates}), 200

if __name__ == '__main__':
    app.run(host='194.146.4.99', port=5000)
