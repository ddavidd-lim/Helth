import pdb
import fitparse
import sys
from datetime import timedelta
import sqlite3

def process_fit_file(file_path):
    fitfile = fitparse.FitFile(file_path)

    timestamps = []
    heart_rates = []
    speeds = []

    first_timestamp = None
    last_timestamp = None
    total_records = 0
    total_heart_rate = 0
    total_speed = 0

    type = None
    for record in fitfile.get_messages("sport"):
        type = record.get_value("sport")

    for record in fitfile.get_messages("record"):
        timestamp = record.get_value('timestamp')
        heart_rate = record.get_value('heart_rate')
        speed = record.get_value('speed')

        if timestamp is not None:
            if first_timestamp is None:
                first_timestamp = timestamp
                # activity_type = record.get_value('activity_type')
            last_timestamp = timestamp
            timestamps.append(timestamp)
        else:
            print("Timestamp is None for record:", record)
            timestamps.append(None)

        if heart_rate is not None:
            heart_rates.append(heart_rate)
            total_heart_rate += heart_rate
        else:
            print("Heart rate is None for record:", record)
            heart_rates.append(None)

        if speed is not None:
            speeds.append(speed)
            total_speed += speed
        else:
            print("Speed is None for record:", record)
            speeds.append(None)

        total_records += 1

    if total_records == 0:
        print("No records found in the file.")
        return

    total_time_seconds = (last_timestamp - first_timestamp).total_seconds()
    total_time = str(timedelta(seconds=total_time_seconds))
    avg_heart_rate = total_heart_rate / total_records
    avg_speed = total_speed / total_records * 2.23694  # Convert m/s to mph
    # total_distance_miles = total_distance / 1609.34  # Convert meters to miles

    print("Activity Type:", type)
    print("Total Time:", total_time)
    print("Date of Activity:", first_timestamp.date())
    print("Average Heart Rate:", avg_heart_rate)
    print("Average Speed (mph):", avg_speed)
    print("Total Distance (miles):", avg_speed * total_time_seconds /3600)

    if type == 'running':
            # Connect to the SQLite database
        conn = sqlite3.connect('C:/Users/andre/StudioProjects/Helth/app/src/main/assets/mydatabase.db')
        cursor = conn.cursor()

        # Insert the values into the database
        cursor.execute("INSERT INTO activity (uid, total_time, date_of_activity, average_heart_rate, average_speed_mph, total_distance_miles) VALUES (?, ?, ?, ?, ?, ?)",
                    (1, total_time, first_timestamp.date(), avg_heart_rate, avg_speed, avg_speed * total_time_seconds /3600))
        aid = cursor.lastrowid

        for activity in range(total_records):
            try:
                cursor.execute("INSERT INTO activity_data (aid, uid, timestamp, heart_rate, speed) VALUES (?, ?, ?, ?, ?)", (aid, 1, timestamps[activity], heart_rates[activity], speeds[activity]))
            except:
                print("error")

        # Commit the transaction and close the connection
        conn.commit()
        conn.close()
    else:
        print(type)

import os
from datetime import datetime
from operator import itemgetter

if __name__ == "__main__":
    directory = "D:/GARMIN/ACTIVITY/"
    files = os.listdir(directory)

    # Filter out non-FIT files and get file creation times
    fit_files = [(filename, os.path.getctime(directory + filename)) for filename in files if filename.endswith('.FIT')]

    # Sort files by creation time
    fit_files.sort(key=itemgetter(1))

    # Process FIT files in order
    for filename, _ in fit_files:
        file_path = directory + filename
        process_fit_file(file_path)



# CREATE TABLE activity (
#     aid INTEGER PRIMARY KEY AUTOINCREMENT,
#     uid INTEGER,
#     total_time TEXT,
#     date_of_activity TEXT,
#     average_heart_rate REAL,
#     average_speed_mph REAL,
#     total_distance_miles REAL
# );
# CREATE TABLE activity_data (
#     aid INTEGER,
#     uid INTEGER,
#     timestamp TEXT,
#     heart_rate REAL,
#     speed REAL,
#     PRIMARY KEY (aid, timestamp),
#     FOREIGN KEY (aid) REFERENCES activity(aid)
# );