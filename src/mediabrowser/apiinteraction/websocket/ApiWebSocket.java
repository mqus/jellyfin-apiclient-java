package mediabrowser.apiinteraction.websocket;

import mediabrowser.apiinteraction.ApiClient;
import mediabrowser.apiinteraction.ApiEventListener;
import mediabrowser.apiinteraction.EmptyResponse;
import mediabrowser.model.apiclient.GeneralCommandEventArgs;
import mediabrowser.model.apiclient.SessionUpdatesEventArgs;
import mediabrowser.model.dto.UserDto;
import mediabrowser.model.extensions.IntHelper;
import mediabrowser.model.extensions.LongHelper;
import mediabrowser.model.extensions.StringHelper;
import mediabrowser.model.logging.ILogger;
import mediabrowser.model.net.WebSocketMessage;
import mediabrowser.model.serialization.IJsonSerializer;
import mediabrowser.model.session.*;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class ApiWebSocket implements ISocketListener {

    private IJsonSerializer jsonSerializer;
    private ILogger logger;
    private ApiEventListener apiEventListener;
    private ApiClient apiClient;

    public ApiWebSocket(IJsonSerializer jsonSerializer, ILogger logger, ApiEventListener apiEventListener, ApiClient apiClient){

        this.jsonSerializer = jsonSerializer;
        this.logger = logger;
        this.apiEventListener = apiEventListener;
        this.apiClient = apiClient;
    }

    public void OpenWebSocket() {

        EnsureWebSocket();
    }

    public void EnsureWebSocket(){

        if (!IsWebSocketOpenOrConnecting()){
            OpenInternal();
        }
    }

    private void OpenInternal(){

        String address = getWebSocketServerAddress();

        URI uri = URI.create(address);

        socketClient = new JavaWebSocketClient(logger, uri, this);

        socketClient.connect();
    }

    public void onOpen(){

        SendIdentificationMessage();
        SendCapabilities();
    }

    private void SendIdentificationMessage(){

        SendWebSocketMessage("Identity", GetIdentificationMessage());
    }

    protected String GetIdentificationMessage()
    {
        return apiClient.getClientName() + "|" + apiClient.getDeviceId() + "|" + apiClient.getApplicationVersion() + "|" + apiClient.getDeviceName();
    }

    private void SendCapabilities(){

        apiClient.ReportCapabilities(apiClient.getCapabilities(), new EmptyResponse());
    }

    private String getWebSocketServerAddress(){

        return apiClient.getServerAddress().replace("http", "ws");
    }

    public void CloseWebSocket(){

        if (IsWebSocketOpen()){

            socketClient.close();
        }
    }

    public void onClose(){

    }

    public void SendWebSocketMessage(String name){
        SendWebSocketMessage(name, "");
    }

    public void SendWebSocketMessage(String name, Object data){

        WebSocketMessage msg = new WebSocketMessage<Object>();

        msg.setMessageType(name);
        msg.setData(data);

        String json = jsonSerializer.SerializeToString(msg);

        SendMessageInternal(json);
    }

    private void SendMessageInternal(String message){
        if (IsWebSocketOpen()){

            socketClient.send(message);
        }
    }

    private JavaWebSocketClient socketClient;
    public boolean IsWebSocketOpen(){

        if (socketClient != null){
            return  socketClient.IsWebSocketOpen();
        }

        return false;
    }

    public boolean IsWebSocketOpenOrConnecting(){

        if (socketClient != null){
            return  socketClient.IsWebSocketOpenOrConnecting();
        }

        return false;
    }

    public void StartReceivingSessionUpdates(int intervalMs)
    {
        SendWebSocketMessage("SessionsStart", intervalMs + "," + intervalMs);
    }

    public void StopReceivingSessionUpdates()
    {
        SendWebSocketMessage("SessionsStop", "");
    }

    public void onMessage(String message){

        String messageType = GetMessageType(message);

        logger.Info("Received web socket message: {0}", messageType);

        if (StringHelper.EqualsIgnoreCase(messageType, "LibraryChanged"))
        {


        }
        else if (StringHelper.EqualsIgnoreCase(messageType, "RestartRequired"))
        {

        }
        else if (StringHelper.EqualsIgnoreCase(messageType, "ServerRestarting"))
        {

        }
        else if (StringHelper.EqualsIgnoreCase(messageType, "ServerShuttingDown"))
        {

        }
        else if (StringHelper.EqualsIgnoreCase(messageType, "UserDeleted"))
        {

        }
        else if (StringHelper.EqualsIgnoreCase(messageType, "ScheduledTaskEnded"))
        {

        }
        else if (StringHelper.EqualsIgnoreCase(messageType, "PackageInstalling"))
        {

        }
        else if (StringHelper.EqualsIgnoreCase(messageType, "PackageInstallationFailed"))
        {

        }
        else if (StringHelper.EqualsIgnoreCase(messageType, "PackageInstallationCompleted"))
        {

        }
        else if (StringHelper.EqualsIgnoreCase(messageType, "PackageInstallationCancelled"))
        {

        }
        else if (StringHelper.EqualsIgnoreCase(messageType, "UserUpdated"))
        {
            WebSocketMessage<UserDto> obj = jsonSerializer.DeserializeFromString(message, new WebSocketMessage<UserDto>().getClass());
            apiEventListener.onUserUpdated(apiClient, obj.getData());
        }
        else if (StringHelper.EqualsIgnoreCase(messageType, "UserConfigurationUpdated"))
        {
            WebSocketMessage<UserDto> obj = jsonSerializer.DeserializeFromString(message, new WebSocketMessage<UserDto>().getClass());
            apiEventListener.onUserConfigurationUpdated(apiClient, obj.getData());
        }
        else if (StringHelper.EqualsIgnoreCase(messageType, "PluginUninstalled"))
        {

        }
        else if (StringHelper.EqualsIgnoreCase(messageType, "Play"))
        {
            WebSocketMessage<PlayRequest> obj = jsonSerializer.DeserializeFromString(message, new WebSocketMessage<PlayRequest>().getClass());
            apiEventListener.onPlayCommand(apiClient, obj.getData());
        }
        else if (StringHelper.EqualsIgnoreCase(messageType, "Playstate"))
        {
            WebSocketMessage<PlaystateRequest> obj = jsonSerializer.DeserializeFromString(message, new WebSocketMessage<PlaystateRequest>().getClass());
            apiEventListener.onPlaystateCommand(apiClient, obj.getData());
        }
        else if (StringHelper.EqualsIgnoreCase(messageType, "NotificationAdded"))
        {

        }
        else if (StringHelper.EqualsIgnoreCase(messageType, "NotificationUpdated"))
        {

        }
        else if (StringHelper.EqualsIgnoreCase(messageType, "NotificationsMarkedRead"))
        {

        }
        else if (StringHelper.EqualsIgnoreCase(messageType, "GeneralCommand"))
        {
            OnGeneralCommand(message);
        }
        else if (StringHelper.EqualsIgnoreCase(messageType, "Sessions"))
        {
            WebSocketMessage<SessionUpdatesEventArgs> obj = jsonSerializer.DeserializeFromString(message, new WebSocketMessage<SessionUpdatesEventArgs>().getClass());
            apiEventListener.onSessionsUpdated(apiClient, obj.getData());
        }
        else if (StringHelper.EqualsIgnoreCase(messageType, "UserDataChanged"))
        {
            WebSocketMessage<UserDataChangeInfo> obj = jsonSerializer.DeserializeFromString(message, new WebSocketMessage<UserDataChangeInfo>().getClass());
            apiEventListener.onUserDataChanged(apiClient, obj.getData());
        }
        else if (StringHelper.EqualsIgnoreCase(messageType, "SessionEnded"))
        {
            WebSocketMessage<SessionInfoDto> obj = jsonSerializer.DeserializeFromString(message, new WebSocketMessage<SessionInfoDto>().getClass());
            apiEventListener.onSessionEnded(apiClient, obj.getData());
        }
        else if (StringHelper.EqualsIgnoreCase(messageType, "PlaybackStart"))
        {
            WebSocketMessage<SessionInfoDto> obj = jsonSerializer.DeserializeFromString(message, new WebSocketMessage<SessionInfoDto>().getClass());
            apiEventListener.onPlaybackStart(apiClient, obj.getData());
        }
        else if (StringHelper.EqualsIgnoreCase(messageType, "PlaybackStopped"))
        {
            WebSocketMessage<SessionInfoDto> obj = jsonSerializer.DeserializeFromString(message, new WebSocketMessage<SessionInfoDto>().getClass());
            apiEventListener.onPlaybackStopped(apiClient, obj.getData());
        }
    }

    private void OnGeneralCommand(String json)
    {
        GeneralCommandEventArgs args = new GeneralCommandEventArgs();

        WebSocketMessage<GeneralCommand> obj = jsonSerializer.DeserializeFromString(json, new WebSocketMessage<GeneralCommand>().getClass());
        args.setCommand(obj.getData());

        args.setKnownCommandType(GeneralCommandType.valueOf(args.getCommand().getName()));

        if (args.getKnownCommandType() != null)
        {
            if (args.getKnownCommandType() == GeneralCommandType.DisplayContent)
            {
                String itemId = args.getCommand().getArguments().get("ItemId");
                String itemName = args.getCommand().getArguments().get("ItemName");
                String itemType = args.getCommand().getArguments().get("ItemType");

                BrowseRequest request = new BrowseRequest();
                request.setItemId(itemId);
                request.setItemName(itemName);
                request.setItemType(itemType);

                apiEventListener.onBrowseCommand(apiClient, request);
                return;
            }
            if (args.getKnownCommandType() == GeneralCommandType.DisplayMessage)
            {
                String header = args.getCommand().getArguments().get("Header");
                String text = args.getCommand().getArguments().get("Text");
                String timeoutMs = args.getCommand().getArguments().get("TimeoutMs");

                long expected = 0;
                tangible.RefObject<Long> tempRef_expected = new tangible.RefObject<Long>(expected);
                LongHelper.TryParseCultureInvariant(timeoutMs, tempRef_expected);
                expected = tempRef_expected.argValue;

                MessageCommand command = new MessageCommand();

                command.setHeader(header);
                command.setText(text);
                command.setTimeoutMs(expected);

                apiEventListener.onMessageCommand(apiClient, command);
                return;
            }
            if (args.getKnownCommandType() == GeneralCommandType.SetVolume)
            {
                String volume = args.getCommand().getArguments().get("Volume");

                int expected = 0;
                tangible.RefObject<Integer> tempRef_expected = new tangible.RefObject<Integer>(expected);
                boolean tempVar = IntHelper.TryParseCultureInvariant(volume, tempRef_expected);
                expected = tempRef_expected.argValue;

                if (tempVar){
                    apiEventListener.onSetVolumeCommand(apiClient, expected);
                }

                return;
            }
            if (args.getKnownCommandType() == GeneralCommandType.SetAudioStreamIndex)
            {
                String index = args.getCommand().getArguments().get("Index");

                int expected = 0;
                tangible.RefObject<Integer> tempRef_expected = new tangible.RefObject<Integer>(expected);
                boolean tempVar = IntHelper.TryParseCultureInvariant(index, tempRef_expected);
                expected = tempRef_expected.argValue;

                if (tempVar){
                    apiEventListener.onSetAudioStreamIndexCommand(apiClient, expected);
                }

                return;
            }
            if (args.getKnownCommandType() == GeneralCommandType.SetSubtitleStreamIndex)
            {
                String index = args.getCommand().getArguments().get("Index");

                int expected = 0;
                tangible.RefObject<Integer> tempRef_expected = new tangible.RefObject<Integer>(expected);
                boolean tempVar = IntHelper.TryParseCultureInvariant(index, tempRef_expected);
                expected = tempRef_expected.argValue;

                if (tempVar){
                    apiEventListener.onSetSubtitleStreamIndexCommand(apiClient, expected);
                }

                return;
            }
            if (args.getKnownCommandType() == GeneralCommandType.SendString)
            {
                String val = args.getCommand().getArguments().get("String");
                apiEventListener.onSendStringCommand(apiClient, val);
                return;
            }
        }

        apiEventListener.onGeneralCommand(apiClient, args.getCommand());
    }

    private String GetMessageType(String json)
    {
        BasicWebSocketMessage message = jsonSerializer.DeserializeFromString(json, BasicWebSocketMessage.class);
        return message.getMessageType();
    }
}