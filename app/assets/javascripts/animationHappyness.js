
window.onload = function(){
    var canvas = document.getElementById('mon_canvas');
    var ctx = canvas.getContext('2d');
    canvas.height = canvas.width;
    var edge = canvas.height;

    var x = edge / 2;
    var y = x;
    var radius = 0.375 * edge;
    var startAngle = 0;
    var endAngle = 2 * Math.PI;


    function drawFace() {
        ctx.beginPath();
        ctx.arc(x, y, radius, startAngle, endAngle);
        ctx.stroke();
        ctx.lineWidth = 5;
        ctx.fillStyle = "yellow";
        ctx.fill();
    }

    function drawHappySmile(){
        var x = edge / 2;
        var y = 0.8 * edge;
        var radius = 0.2 * edge;
        var startAngle = 1.1 * Math.PI;
        var endAngle = 1.9 * Math.PI;

        ctx.beginPath();
        ctx.arc(x, y, radius, startAngle, endAngle);
        ctx.lineWidth = 7;

        // line color
        ctx.strokeStyle = 'black';
        ctx.stroke();
    }

    function drawNeutralSmile(){
    }

    function dra


    function drawEyes(){
        var centerX = 0.2 * edge;
        var centerY = 0;
        var radius = 0.05 * edge;

        var scaleX = 0.5;
        var scaleY = 1;

        // save state
        ctx.save();

        // translate context so height is 1/3'rd from top of enclosing circle
        ctx.translate(canvas.width / 2, canvas.height / 3);

        // scale context horizontally by 50%
        ctx.scale(scaleX, scaleY);

        // draw circle which will be stretched into an oval
        ctx.beginPath();
        ctx.arc(centerX, centerY, radius, 0, 2 * Math.PI, false);

        // restore to original state
        ctx.restore();

        // apply styling
        ctx.fillStyle = 'black';
        ctx.fill();
        ctx.lineWidth = 2;
        ctx.strokeStyle = 'black';
        ctx.stroke();

        //left eye
        centerX = -centerX;

        // save state
        ctx.save();

        // translate context so height is 1/3'rd from top of enclosing circle
        ctx.translate(canvas.width / 2, canvas.height / 3);

        // scale context horizontally by 50%
         ctx.scale(scaleX, scaleY);

        // draw circle which will be stretched into an oval
        ctx.beginPath();
        ctx.arc(centerX, centerY, radius, 0, 2 * Math.PI, false);

        // restore to original state
        ctx.restore();

        // apply styling
        ctx.fillStyle = 'black';
        ctx.fill();
        ctx.lineWidth = 2;
        ctx.strokeStyle = 'black';
        ctx.stroke();
    }


    function drawSadFace(){
       drawFace();
       drawEyes();
       drawSmile();
    }
    function drawSadFace(){
       drawFace();
       drawEyes();
       drawSmile();
    }
    function drawSadFace(){
       drawFace();
       drawEyes();
       drawSmile();
    }

    drawHappyFace();




//    var myInterval = setInterval(animate, 1000/30);
//
//    var tailleMini = 10;
//    var tailleMax = canvas.width;
//
//    var tailleActuelle = tailleMini+1;
//
//    var xPos = 0;
//        var yPos = 0;
//        var vitesse = 1;
//
//    function animate(){
//    context.clearRect(0, 0, canvas.width, canvas.height);
//
//
//        xPos = (canvas.width/2)-(tailleActuelle/2);
//        yPos = xPos;
//
//        context.fillRect(xPos, yPos, tailleActuelle, tailleActuelle);
//
//        //On fait les tests pour savoir si l'on va devoir agrandir ou rétrécir notre carré.
//        if(tailleActuelle >= tailleMax || tailleActuelle <= tailleMini)
//        {
//            vitesse *= -1;
//        }
//
//        tailleActuelle += vitesse;
//    }


};

