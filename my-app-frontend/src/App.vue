<template>
  <v-app id="inspire">
    <v-app-bar app color=#bd1f36 flat>
      <v-row align="center" justify="space-around">
        
        <v-btn-toggle rounded >
          <v-btn @click="saveXML()" depressed>saveXML</v-btn>
          <v-btn @click="loadXML()" depressed color="gray">loadXML</v-btn>
          <v-btn @click="saveJSON()" depressed>saveJSON</v-btn>
          <v-btn @click="loadJSON()" depressed color="gray">loadJSON</v-btn>
        </v-btn-toggle>   

        
          <v-btn @click="redo()" depressed color="gray">redo</v-btn>
          <v-btn @click="undo()" depressed>undo</v-btn>
        
        <v-btn-toggle rounded v-model="coco" >
          <v-btn @click="move()" depressed>Move</v-btn>
          <v-btn @click="colorTheShape()" depressed>Recolor</v-btn>
          <v-btn @click="copy()" depressed color="gray">Copy</v-btn>
          <v-btn @click="deleteShape()" depressed color="gray">Delete</v-btn>
          <v-btn @click="resize()" depressed>Resize</v-btn>
        </v-btn-toggle>

      </v-row>
    </v-app-bar>

    <v-main class="grey lighten-3">
      <v-container >
        <v-row >
          <v-col cols="12" sm="2" >
            <v-sheet rounded="lg" min-height="268">
              <v-btn width = 100% disabled>Fill color</v-btn>
              <v-color-picker  dot-size="25" hide-inputs show-swatches swatches-max-height="50" v-model="color"></v-color-picker>          
            </v-sheet>
            <v-sheet rounded="lg" min-height="268" >
              <v-btn width = 100% disabled>Stroke color</v-btn>
              <v-color-picker   dot-size="25" hide-inputs show-swatches swatches-max-height="50" v-model="sColor"></v-color-picker>
            </v-sheet>
          </v-col>

          <v-col cols="12"  lg="8">
            <v-sheet min-height="70vh" rounded="lg">
              <div class="my-canvas-wrapper">
                <canvas ref="my-canvas" width="766" height="620"></canvas>
                <slot></slot>
              </div>
            </v-sheet>
          </v-col>

          <v-col cols="12" sm="2">
            <v-sheet rounded="lg" min-height="268">
              <v-card class="mx-auto" max-width="300" tile>
                <v-list flat>
                  <v-subheader>Shapes</v-subheader>
                  <v-list-item-group  color=#da1e37>
                    <v-list-item
                      v-for="(shape, i) in shapes"
                      :key="i"
                      @click="shapefun(shape)"
                    >
                      <v-list-item-content>
                        <v-list-item-title v-text="shape"></v-list-item-title>
                      </v-list-item-content>
                    </v-list-item>
                  </v-list-item-group>
                </v-list>
              </v-card>
            </v-sheet>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>



<script>
import Vue from "vue";
import axios from "axios";
import VueAxios from "vue-axios";
export default {
  data() {
    return {
     createdShapes : [],
      
      // this object to save infor about what user choose
      object:{
        ID:0,
        type:"",
        BorderColor:"",
        FillColor:"",
        BorderWeight:"",
        numberOfSides : 0 ,
        dragStartLocation:{x:0,y:0},
        position:{x:0,y:0}
      },
      
      shapes: ["circle", "square", "rectangle", "triangle", "ellipse", "line"],
      canvas: null,
      ctx: null,
      dragging: false,
      snapshot: "",
      angle: 45 * (Math.PI / 180),
      color: "",
      sColor:"",
      isMove: false,
      isDelete: false,
      isCopy: false,
      isResize: false,
      isColor : false ,
      selectedShapeIndex: 0,
      found : false ,
      coco : 10 ,
    };
  },
  mounted() {
    var vm = this;
    vm.canvas = vm.$refs["my-canvas"];
    vm.ctx = vm.canvas.getContext("2d");
    window.addEventListener('load', this.init, false);
  },


  methods: {
    init: function() {
      var vm = this;
      vm.ctx.strokeStyle = "red";
      vm.ctx.lineWidth = 6;
      vm.ctx.lineCap = "round";
      vm.ctx.fillStyle = "red";
      vm.canvas.addEventListener("mousedown", this.dragStart, false);
      vm.canvas.addEventListener("mousemove", this.drag, false);
      vm.canvas.addEventListener("mouseup", this.dragStop, false);
      axios.post('http://localhost:8080/initialization',{});
      this.drawAll();
    },

    shapefun: function(shapeName) {
      this.coco = 5 ;
      this.isMove= false;
      this.isDelete= false;
      this.isCopy= false;
      this.isResize=false;
      this.isColor = false ;
      if (shapeName === "line") {
        this.object.numberOfSides = 1;
        this.object.type = "line";
      }
      else if (shapeName === "circle") {
        this.object.type = "circle";
      }
      else if (shapeName === "rectangle") {
        this.object.numberOfSides = 4;
        this.object.type = "rectangle";
      }
      else if (shapeName === "ellipse") {
        this.object.type = "ellipse";
      }
      else if (shapeName === "square") {
        this.object.type = "square";
        this.object.numberOfSides = 4;
        this.angle = 45 * (Math.PI / 180);
      }
      else if (shapeName === "triangle") {
        this.object.type = "triangle";
        this.object.numberOfSides = 3;
        this.angle = 90 * (Math.PI / 180);
      }
      console.log(shapeName);
    },

    move: function() {
      this.isMove= true,
      this.isDelete= false,
      this.isCopy= false,
      this.isResize=false,
      this.isColor = false ,
      console.log("move");
    },

    copy: function() {
      this.isMove= false,
      this.isDelete= false,
      this.isCopy= true,
      this.isResize=false,
      this.isColor = false ,
      console.log("copy");
    },

    deleteShape: function() {
      this.isMove= false,
      this.isDelete= true,
      this.isCopy= false,
      this.isResize=false,
      this.isColor = false ,
      console.log("deleteShape");
    },

    resize: function() {
      this.isMove = false;
      this.isResize = true;
      this.isCopy = false;
      this.isDelete = false;
      console.log("resize");
    },

    colorTheShape: function(){
      this.isMove= false,
      this.isDelete= false,
      this.isCopy= false,
      this.isResize=false,
      this.isColor = true ,
      console.log("color");
    },

    getCanvasCoordinates: function(event) {
      var vm = this;
      var x = event.clientX - vm.canvas.getBoundingClientRect().left,
        y = event.clientY - vm.canvas.getBoundingClientRect().top;

      return { x: x, y: y };
    },

    takeSnapshot: function() {
      var vm = this;
      this.snapshot = vm.ctx.getImageData(0,0,vm.canvas.width,vm.canvas.height);
    },

    restoreSnapshot: function() {
      var vm = this;
      vm.ctx.putImageData(this.snapshot, 0, 0);
    },
    
    drawLine: function (x1, y1 ,x2  , y2) {
        var vm = this;
        vm.ctx.beginPath();
        vm.ctx.moveTo(x1, y1);
        vm.ctx.lineTo(x2, y2);
        vm.ctx.stroke();
    },

    drawCircle :function(x1, y1 , x2 , y2 ) {
        var vm = this;
        var radius = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
        vm.ctx.beginPath();
        vm.ctx.arc(x1,y1, radius, 0, 2 * Math.PI, false);
        vm.ctx.fill();
        vm.ctx.stroke();
    },

    drawRectangle : function (x1, y1 ,x2  , y2 ){
      this.ctx.beginPath();
      this.ctx.fillRect(x1, y1 , x2 - x1, (y2 -y1) );
      this.ctx.strokeRect(x1, y1 , x2 - x1, (y2 -y1));
    },

    drawPolygon : function(dragStartLocation,position, sides, angle) {
      var coordinates = [],
          radius = Math.sqrt(Math.pow((dragStartLocation.x - position.x), 2) + Math.pow((dragStartLocation.y - position.y), 2)),
          index = 0;

      for (index = 0; index < sides; index++) {
          coordinates.push({x: dragStartLocation.x + radius * Math.cos(angle), y: dragStartLocation.y - radius * Math.sin(angle)});
          angle += (2 * Math.PI) / sides;
      }

      this.ctx.beginPath();
      this.ctx.moveTo(coordinates[0].x, coordinates[0].y);
      for (index = 1; index < sides; index++) {
          this.ctx.lineTo(coordinates[index].x, coordinates[index].y);
      }
      this.ctx.closePath();
      this.ctx.stroke();
    },

    drawEllipse :function(x1, y1, x2, y2) {
        var radiusX = (x2 - x1) * 0.5,   /// radius for x based on input
            radiusY = (y2 - y1) * 0.5,   /// radius for y based on input
            centerX = x1 + radiusX,      /// calc center
            centerY = y1 + radiusY,
            step = 0.01,                 /// resolution of ellipse
            a = step,                    /// counter
            pi2 = Math.PI * 2 - step;    /// end angle
        this.ctx.beginPath();
        /// set start point at angle 0
        this.ctx.moveTo(centerX + radiusX * Math.cos(0),
                centerY + radiusY * Math.sin(0));
        for(; a < pi2; a += step) {
            this.ctx.lineTo(centerX + radiusX * Math.cos(a),
                    centerY + radiusY * Math.sin(a));
        }
        this.ctx.closePath();
        this.ctx.stroke();
    },

    draw :function (position) {
           // change the color
          this.ctx.fillStyle = this.color;
          this.object.FillColor = this.color;
          this.ctx.strokeStyle = this.sColor;
          this.object.BorderColor = this.sColor;
          /////// Save the information here //////
          if (this.object.type === "line") {
             this.drawLine(this.object.dragStartLocation.x, this.object.dragStartLocation.y , position.x  , position.y);
          }
          else if (this.object.type === "circle") {
             this.drawCircle(this.object.dragStartLocation.x, this.object.dragStartLocation.y , position.x  , position.y);
          }
          else if (this.object.type === "rectangle") {
              this.drawRectangle(this.object.dragStartLocation.x, this.object.dragStartLocation.y , position.x  , position.y );
          }
          else if (this.object.type === "ellipse") {
            this.drawEllipse(this.object.dragStartLocation.x, this.object.dragStartLocation.y , position.x  , position.y );
          }
          else if (this.object.type === "square") {
            this.drawPolygon(this.object.dragStartLocation,position,4,45 * (Math.PI / 180));
          }
          else if (this.object.type === "triangle") {
            this.drawPolygon(this.object.dragStartLocation,position,3,90 * (Math.PI / 180));
          }
          this.ctx.fill();
    },

     drawAll : function(){
      this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
      for (var i = 0 ; i < this.createdShapes.length ; i++){
        var shape = this.createdShapes[i];
        this.ctx.fillStyle = shape.fillColor;
        this.ctx.strokeStyle = shape.borderColor;
        if (shape.type === "line") {
          this.drawLine(shape.points[0].x, shape.points[0].y , shape.points[1].x  , shape.points[1].y);
        }
        else if (shape.type === "circle") {
          this.drawCircle(shape.points[0].x, shape.points[0].y , shape.points[1].x  , shape.points[1].y);
        }
        else if (shape.type === "rectangle") {
          this.drawRectangle(shape.points[0].x, shape.points[0].y , shape.points[1].x  , shape.points[1].y );
        }
        else if (shape.type === "ellipse") {
          this.drawEllipse(shape.points[0].x, shape.points[0].y , shape.points[1].x  , shape.points[1].y );
        }
        else if (shape.type === "square") {
          this.drawPolygon(shape.points[0] , shape.points[1], 4,45 * (Math.PI / 180));
        }
        else if (shape.type === "triangle") {
          this.drawPolygon(shape.points[0] , shape.points[1] ,  3,90 * (Math.PI / 180));
        }
        this.ctx.fill();
      }
    },


    dragStart: function(event) {
      this.dragging = true;
      this.object.dragStartLocation = this.getCanvasCoordinates(event);
      this.takeSnapshot();
       if (this.isMove === true || this.isCopy === true || this.isDelete === true || this.isResize === true || this.isColor === true){
            ///////// import the shapes data /////////
            this.found = false ;
            for(var i=this.createdShapes.length-1;i>=0;i--){
                if(this.isMouseInShape(this.object.dragStartLocation.x,this.object.dragStartLocation.y,this.createdShapes[i])){
                  this.found = true ;
                    this.selectedShapeIndex=i;
                    this.dragging = true;
                    break;
                }
            }

            if (!this.found){
              return;
            }

            if (this.isCopy === true){
                var newShape = JSON.parse(JSON.stringify(this.createdShapes[this.selectedShapeIndex]));
                console.log(newShape);
                newShape.points[1].x +=25;
                newShape.points[1].y +=20;
                newShape.points[0].x +=25;
                newShape.points[0].y +=20;
                this.createdShapes.push(newShape); 
                this.selectedShapeIndex = this.createdShapes.length -1;
                this.drawAll();
            }
            else if (this.isDelete === true){
                //////// send the shap to backend to delete it////////
                /////// get the new shapes list /////////
                var shape = this.createdShapes[this.selectedShapeIndex];
                if (shape.type == "circle" ||shape.type === "ellipse"){
                  this.deleteEliptical(shape);
                }
                else{
                  this.deletePolygon(shape);
                }
                this.dragging = false;
            }
            else if (this.isColor === true){
                var selectedShape = this.createdShapes[this.selectedShapeIndex];
                selectedShape.fillColor = this.color;
                selectedShape.borderColor = this.sColor;
                ////////// send this object to backend : this.createdShapes[this.selectedShapeIndex]   /////////
                var shape = selectedShape;
                if (shape.type == "circle" ||shape.type === "ellipse"){
                  this.editEliptical(shape);
                }
                else{
                  this.editPolygon(shape);
                }
                this.drawAll();
                this.dragging = false;
            }
        }
        else {
            this.dragging = true;
        }
    },

    drag :function(event) {
           var position;
        if (this.dragging === true) {
           position = this.getCanvasCoordinates(event);
            if (this.isMove === true || this.isCopy === true || this.isResize === true){
             if(!this.found) return;
              var dx=position.x - this.object.dragStartLocation.x;
              var dy=position.y - this.object.dragStartLocation.y;
              // move the selected shape by the drag distance
              var selectedShape=this.createdShapes[this.selectedShapeIndex];
              selectedShape.points[1].x += dx;
              selectedShape.points[1].y += dy;
              if (this.isResize === false){
                  selectedShape.points[0].x +=dx;
                  selectedShape.points[0].y +=dy;
              }
              // clear the canvas and redraw all shapes
              this.drawAll();
              // update the starting drag position (== the current mouse position)
              this.object.dragStartLocation.x=position.x;
              this.object.dragStartLocation.y=position.y;
            }
            else if (this.isDelete != true){
              this.restoreSnapshot();
              this.draw(position);    
            }
        }

    },

    dragStop :function (event){
        this.dragging = false;
        if (this.isMove === true || this.isResize === true){
          if(!this.found) return;
          ///////////////// send this obj to backend : this.createdShapes[this.selectedShapeIndex]  ////////
          var shape = this.createdShapes[this.selectedShapeIndex];
          if (shape.type === "circle" ||shape.type === "ellipse"){
            this.editEliptical(shape);
          }
          else{
            this.editPolygon(shape);
          }
        }
        else if (this.isCopy === true){
          if(!this.found) return;
          var shape = this.createdShapes[this.selectedShapeIndex];
          if (this.object.type === "circle" ||this.object.type === "ellipse"){
            this.convertToObject(shape);
            this.sendEliptical();
          }
          else{
            this.convertToObject(shape);
            this.sendPolygon();
          }
        }
        else if (this.isColor != true && this.isDelete != true && this.isColor!=true){
            this.restoreSnapshot();
            var position = this.getCanvasCoordinates(event);
            this.object.position=position;
            this.draw(position);
              if (this.object.type === "circle" ||this.object.type === "ellipse"){
                  this.sendEliptical(); }
             else{
                this.sendPolygon();
              }
        }
    },

    isMouseInShape :function(mx,my,shape){
        if(shape.type === "circle"){
            var dx=mx-shape.points[0].x;
            var dy=my-shape.points[0].y;
            var radius = Math.sqrt(Math.pow((shape.points[0].x - shape.points[1].x), 2) + Math.pow((shape.points[0].y - shape.points[1].y), 2));
            // math test to see if mouse is inside circle
            if(dx*dx+dy*dy<radius*radius){
                return(true);
            }
        }
        else if(shape.type === "ellipse"){
             var radiusX = (shape.points[1].x - shape.points[0].x) * 0.5,
             radiusY = (shape.points[1].y - shape.points[0].y) * 0.5,
             centerX = shape.points[0].x + radiusX, 
             centerY = shape.points[0].y + radiusY;
             dx=mx-centerX;
             dy=my-centerY;
            // math test to see if mouse is inside ellipse
            return ( (dx*dx) / (radiusX * radiusX) + (dy*dy) / (radiusY * radiusY) <=1 );
        }
        else if(shape.type === "rectangle"){
            var rLeft = Math.min(shape.points[0].x,shape.points[1].x);
            var rRight = Math.max(shape.points[0].x,shape.points[1].x);
            var rTop = Math.min(shape.points[0].y,shape.points[1].y);
            var rBott = Math.max(shape.points[0].y,shape.points[1].y);
            // math test to see if mouse is inside rectangle
            if( mx>rLeft && mx<rRight && my>rTop && my<rBott){
                return(true);
            }
        }
        else if(shape.type === "triangle" || shape.type === "square"){
          if(shape.type === "triangle"){ var sides =3 ,angle= 90 * (Math.PI / 180)}
          else if(shape.type === "square"){ sides =4 ,angle= 45 * (Math.PI / 180)}
          var coordinates = [],
          index = 0;
          radius = Math.sqrt(Math.pow((shape.points[0].x - shape.points[1].x), 2) + Math.pow((shape.points[0].y - shape.points[1].y), 2));
          for (index = 0; index < sides; index++) {
              coordinates.push({x: shape.points[0].x + radius * Math.cos(angle), y: shape.points[0].y - radius * Math.sin(angle)});
              angle += (2 * Math.PI) / sides;
          }
          let path = new Path2D();
          path.moveTo(coordinates[0].x, coordinates[0].y);
          for (index = 1; index < sides; index++) {
              path.lineTo(coordinates[index].x, coordinates[index].y);
          }
          path.closePath();
           if (this.ctx.isPointInPath(path, mx, my)) {
              return(true);
            }
        }
        else if(shape.type === "line"){
            let path = new Path2D();
            path.moveTo(shape.points[0].x-10, shape.points[0].y-10);
            path.lineTo(shape.points[1].x-10, shape.points[1].y-10);
            path.lineTo(shape.points[1].x+10, shape.points[1].y+10);
            path.lineTo(shape.points[0].x+10, shape.points[0].y+10);
            path.closePath();
            if(this.ctx.isPointInPath(path, mx, my)){
                return(true);
            }
        }
        // the mouse isn't in any of the shapes
        return(false);
    },

    convertToObject(shape){
      this.object.ID = 0;
      this.object.BorderColor= shape.borderColor;
      this.object.FillColor= shape.fillColor;
      this.object.BorderWeight= shape.borderWeight;
      this.object.type = shape.type;
      this.object.numberOfSides = shape.numberOfSides ;
      this.object.dragStartLocation = shape.points[0];
      this.object.position = shape.points[1];
    },

    sendEliptical :function(){
      axios.post('http://localhost:8080/addElipticalShapes',{
          json : "ElipticalShapes",
          id: 0,
          borderWeight: 1,
          fillColor:this.object.FillColor,
          borderColor: this.object.BorderColor,
          type : this.object.type,
          points : [this.object.dragStartLocation,this.object.position]
          }).then(Response=>{
            const Data = Response.data;
            this.createdShapes = Data ;
      });
    },

    sendPolygon :function(){
      axios.post('http://localhost:8080/addPolygonShapes',{
          json : "polygon",
          id: 0,
          borderWeight: 55,
          fillColor:this.object.FillColor,
          borderColor: this.object.BorderColor,
          NumberOfSides : this.object.numberOfSides,
          type : this.object.type,
          points : [this.object.dragStartLocation,this.object.position]
          }).then(Response=>{
            const Data = Response.data;
            this.createdShapes = Data ;
      });
    },

    editEliptical : function(shape){
      axios.post('http://localhost:8080/EditEliptical',{
        json : "ElipticalShapes",
        id: shape.id,
        borderWeight: shape.borderWeight,
        fillColor: shape.fillColor,
        borderColor: shape.borderColor,
        type : shape.type,
        points : shape.points,
      }).then(Response=>{
          const Data = Response.data;
          this.createdShapes = Data ;
      });
    },

    editPolygon : function(shape){
      axios.post('http://localhost:8080/EditPolygon',{
        json : "polygon",
        id: shape.id,
        borderWeight: shape.borderWeight,
        fillColor: shape.fillColor,
        borderColor: shape.borderColor,
        type : shape.type,
        points : shape.points,
      }).then(Response=>{
          const Data = Response.data;
          this.createdShapes = Data ;
      });
    },

    deleteEliptical : function(shape){
      axios.post('http://localhost:8080/DeleteEliptical',{
        json : "ElipticalShapes",
        id: shape.id,
        borderWeight: shape.borderWeight,
        fillColor: shape.fillColor,
        borderColor: shape.borderColor,
        type : shape.type,
        points : shape.points,
      }).then(Response=>{
          const Data = Response.data;
          this.createdShapes = Data ;
          this.drawAll();
      });
    },

    deletePolygon : function(shape){
      axios.post('http://localhost:8080/DeletePolygons',{
        json : "polygon",
        id: shape.id,
        borderWeight: shape.borderWeight,
        fillColor: shape.fillColor,
        borderColor: "1",
        type : shape.type,
        points : shape.points,
      }).then(Response=>{
          const Data = Response.data;
          this.createdShapes = Data ;
          this.drawAll();
      });
    },


    redo: function() {
      axios.post('http://localhost:8080/Redo',{
        }).then(Response=>{
          const Data = Response.data;
          this.createdShapes = Data;
          this.drawAll();
      });
      console.log("redo");
    },

    undo: function() {
      axios.post('http://localhost:8080/Undo',{
        }).then(Response=>{
          const Data = Response.data;
          this.createdShapes = Data;
          this.drawAll();
      });
      console.log("undo");
    },

    saveXML: function() {
      axios.post('http://localhost:8080/SaveXML',{});
      console.log("saveXML");
    },

    loadXML: function() {
      axios.post('http://localhost:8080/LoadXML',{
        }).then(Response=>{
          const Data = Response.data;
          this.createdShapes = Data;
          this.drawAll();
      });
      console.log("loadXML");
    },

    saveJSON: function() {
      axios.post('http://localhost:8080/SaveJSON',{});
      console.log("saveJSON");
    },

    loadJSON: function() {
      axios.post('http://localhost:8080/LoadJSON',{
        }).then(Response=>{
          const Data = Response.data;
          this.createdShapes = Data;
          this.drawAll();
      });
      console.log("loadJSON");
    },
  }
};

</script>

<style></style>
