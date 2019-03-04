from flask import Flask, render_template
app = Flask(__name__)
#routes

@app.route("/")
def index():
    return render_template("index.html")

@app.route("/welcome")
def welcome(name):
    return render_template("welcome.html", name=name)

@app.route("/roster")
def roster(grade_view):
    class_roster =[("Yohannes", 94, "Junior"), ("Zach", 90, "Junior"), ("Anthony", 89, "Sophmore"), ("Brandon", 83, "Junior"), ("Garrison", 85, "Freshman"), ("Xavier", 84, "Junior"), ("Aidan", 79, "Senior")]
    return render_template("roster.html", grade_view=grade_view, class_roster=class_roster)
    
if __name__ == "__main__":
    app.run()
