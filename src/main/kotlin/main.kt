import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import model.Message
import model.SampleData

fun main() = application {
    val icon = painterResource("app_icon.png")
    Window(
        onCloseRequest = ::exitApplication,
        title = "Can's First App",
        icon = icon
    ) {
        MaterialTheme {
            conversation(SampleData.conversationSample)
            //messageCard(Message("Can", "This is a message from Can"))
        }

    }

}

@Composable
fun messageCard(message: Message) {

    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource("profile_picture.png"),
            contentDescription = "profile picture",
            modifier = Modifier
                //set image size to 40dp
                .size(90.dp)
                //clip image as circle
                .clip(CircleShape)
                //add border
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )

        //adds space between row(image in our case) and Column(text)
        //like margin. Width-> horizontal space
        Spacer(modifier = Modifier.width(10.dp))

        //default olarak isExpanded'i false olarak atadım
        //Column'ın clickable'Inda bunu kullanıyorum. Nasıl?
        //Ilk tıklanmada false olan değerin NOT'ını yani true değerini isExpanded'a atıyorum
        //Yani genişletildi flag'i tutuyorum. Tekrar tıklayınca false değerini atıyorum yani
        //artık geniş değil. isExpanded true olunca altta bütün bilgileri göster, false ise sadece
        //ilk satırı göster demeyi unutma
        var isExpanded by remember { mutableStateOf(false) }
        val surfaceColor: Color by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary else Color.Gray
        )

        Surface(
            shape = MaterialTheme.shapes.large,
            border = BorderStroke(2.dp, color = Color.DarkGray),
            elevation = 5.dp,
        ) {
            Column(modifier = Modifier.clickable { isExpanded = isExpanded.not() }
                .padding(all = 8.dp)) {
                Text(
                    text = message.author,
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.subtitle2
                )
                //add vertical space with height
                Spacer(modifier = Modifier.height(10.dp))

                Surface(
                    shape = MaterialTheme.shapes.medium,
                    elevation = 2.dp,
                    color = surfaceColor,
                    modifier = Modifier.animateContentSize().padding(1.dp)
                ) {
                    Text(
                        text = message.body,
                        modifier = Modifier.padding(all = 4.dp),
                        // If the message is expanded, we display all its content
                        // otherwise we only display the first line
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                        style = MaterialTheme.typography.body2,
                    )
                }
            }
        }
    }
}

@Composable
fun conversation(messageList: List<Message>) {
//    LazyColumn(
//        //adds 16dp padding to all items for horizontal
//        //adds 8dp padding to top and bottom but not between all items
//        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
//        //adds space in-between the items
//        verticalArrangement = Arrangement.spacedBy(4.dp)
//    ) {
//        //add single item
//        item {
//            Text(text = "First item")
//        }
//        //add 5 items
//        items(5) { index ->
//            Text(text = "Item: $index")
//        }
//    }
    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(messageList) { message ->
            messageCard(message)
        }
    }

}

//@Composable
//fun previewMessageCard() {
//     messageCard(message = Message("Vicky","Hearth"))
//}

//    var text by remember { mutableStateOf("Hello, World!") }
//
//    MaterialTheme {
//        Button(onClick = {
//            text = "Hello, Desktop!"
//        }) {
//            Text(text)
//        }
//    }